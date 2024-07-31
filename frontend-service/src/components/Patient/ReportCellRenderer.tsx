import { useState } from 'react';
import { Button } from "@mui/material";
import { getDiabetesReport } from '../../services/api';

const ReportCellRenderer = (props: any) => {
    const [clicked, setClicked] = useState(false);
    const [report, setReport] = useState<string | null>(null);

    const handleEvaluateRisk = async () => {
        try {
            const response = await getDiabetesReport(props.data.id);
            setReport(response.data.riskLevel);
            setClicked(true);
        } catch (error) {
            console.error('Error evaluating risk:', error);
        }
    }

    return (
        <div style={{ display: 'flex', alignItems: 'center' }}>
            <Button
                variant="contained"
                color="primary"
                sx={{ width: 60, fontSize: '0.7em', marginTop: '8px' }}
                onClick={handleEvaluateRisk}
                disabled={clicked}
            >
                Évaluer
            </Button>
            {report && <span style={{ marginLeft: '10px' }}>{report}</span>}
        </div>
    );
}

export default ReportCellRenderer;