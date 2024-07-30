import React, { useState, useEffect } from 'react';
import { Modal, Box, Typography, Button, List, ListItem, ListItemText, IconButton, TextField } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import EditIcon from '@mui/icons-material/Edit';
import { getPatientNotes, addNote, updateNote, deleteNote, deletePatientNotes } from '../../services/api';
import { Note, Patient } from '../../types/types';

interface NoteModalProps {
    patient: Patient;
    onClose: () => void;
}

const NoteModal: React.FC<NoteModalProps> = ({ patient, onClose }) => {
    const [notes, setNotes] = useState<Note[]>([]);
    const [editingNote, setEditingNote] = useState<Note | null>(null);
    const [newNote, setNewNote] = useState<string>("");

    useEffect(() => {
        fetchNotes();
    }, [patient]);

    const fetchNotes = async () => {
        try {
            const response = await getPatientNotes(String(patient.id));
            setNotes(response.data);
        } catch (error) {
            console.error('Error fetching patient notes:', error);
        }
    };

    const handleSaveNote = async () => {
        if (newNote !== "") {
            try {
                const response = await addNote({ patientId: String(patient.id), patientName: patient.lastName, note: newNote });
                setNotes([...notes, response.data]);
                setNewNote("");
            } catch (error) {
                console.error('Error saving new note:', error);
            }
        } 
    };

    const handleUpdateNote = async (note: Note) => {
        try {
            const response = await updateNote(note.id, note);
            setNotes(notes.map(n => (n.id === note.id ? response.data : n)));
            setEditingNote(null);
        } catch (error) {
            console.error('Error updating note:', error);
        }  
    };

    const handleDeleteNote = async (id: string) => {
        try {
            await deleteNote(id);
            setNotes(notes.filter(note => note.id !== id));
        } catch (error) {
            console.error('Error deleting note:', error);
        }
        
    };

    const handleDeleteAllNotes = async () => {
        try {
            await deletePatientNotes(String(patient.id));
            setNotes([]);
        } catch (error) {
            console.error('Error deleting patient notes:', error);
        }
    };
    
    return (
        <Modal open={true} onClose={onClose}>
            <Box sx={{
                position: 'absolute',
                top: '50%',
                left: '50%',
                transform: 'translate(-50%, -50%)',
                width: 400,
                maxHeight: '80%',
                bgcolor: 'background.paper',
                boxShadow: 24,
                p: 4,
                overflow: 'auto'
              }}>
                <Typography variant="h6" gutterBottom color={'black'}>Notes pour {patient.firstName} {patient.lastName}</Typography>
                <List>
                    {notes.map(note => (
                        <ListItem key={note.id} sx={{ border: '1px solid #ddd', borderRadius: '4px', marginBottom: '8px', padding: '8px' }}>
                            <ListItemText 
                                primary={<span style={{ color: 'black', fontStyle: 'italic' }}>{note.note}</span>} 
                                secondary={new Date(note.date).toLocaleString()} 
                            />
                            <IconButton onClick={() => handleDeleteNote(note.id)}><CloseIcon /></IconButton>
                            <IconButton onClick={() => setEditingNote(note)}><EditIcon /></IconButton>
                        </ListItem>
                    ))}
                </List>
                {editingNote ? (
                    <Box>
                        <TextField
                            fullWidth
                            multiline
                            rows={4}
                            value={editingNote.note}
                            onChange={(e) => setEditingNote({ ...editingNote, note: e.target.value })}
                        />
                        <Button onClick={() => handleUpdateNote(editingNote)} sx={{color:'white', bgcolor:'#166ac6'}}>Éditer</Button>
                    </Box>
                ) : (
                    <Box>
                        <TextField
                            fullWidth
                            multiline
                            rows={4}
                            placeholder="Ajouter une note"
                            value={newNote}
                            onChange={(e) => setNewNote(e.target.value)}
                        />
                        <Button onClick={handleSaveNote} sx={{color:'white', bgcolor:'#166ac6'}}>Enregistrer la note</Button>
                    </Box>
                )}
                <Button onClick={handleDeleteAllNotes} sx={{color:'white', bgcolor:'#b82828'}}>Supprimer toutes les notes</Button>
            </Box>
        </Modal>
      );
}

export default NoteModal;