export interface Patient {
    id: number;
    firstName: string;
    lastName: string;
    birthdate: string;
    gender: string;
    address?: string;
    phoneNumber?: string;
}

export interface Note {
    id: string;
    patientId: string;
    patientName: string;
    note: string;
    date: Date;
}