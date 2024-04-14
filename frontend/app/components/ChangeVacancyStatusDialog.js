import React, {useEffect, useState} from 'react';
import axios from 'axios';

function ChangeVacancyStatusDialog({vacancyId, onClose, fetchVacancies}) {
    const [newStatus, setNewStatus] = useState('');
    const [statusOptions, setStatusOptions] = useState([]);

    useEffect(() => {
        fetchStatusOptions();
    }, []);

    const fetchStatusOptions = async () => {
        try {
            const response = await axios.get('http://localhost:8080/hiring-status');
            setStatusOptions(response.data);
        } catch (error) {
            console.error('Error fetching status options:', error);
        }
    };

    const changeVacancyStatus = async (e) => {
        e.preventDefault();
        try {
            await axios.put(`http://localhost:8080/vacancies/${vacancyId}/status`, {newStatus});
            fetchVacancies(); // Обновляем список вакансий после изменения статуса
            onClose();
        } catch (error) {
            console.error('Error changing vacancy status:', error);
        }
    };

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <h2>Изменить статус вакансии</h2>
                <form onSubmit={changeVacancyStatus} className="change-status-form">
                    <div>
                        <select value={newStatus} onChange={(e) => setNewStatus(e.target.value)} required>
                            <option value="">Выберите статус</option>
                            {statusOptions.map(option => (
                                <option key={option.id} value={option.value}>{option.name}</option>
                            ))}
                        </select>
                    </div>
                <h2></h2>
                <div className="button-group">
                    <button type="submit" className="create-button">Изменить</button>
                    <button type="button" className="cancel-button" onClick={onClose}>Отмена</button>
                </div>
            </form>
        </div>
</div>
)
    ;
}

export default ChangeVacancyStatusDialog;
