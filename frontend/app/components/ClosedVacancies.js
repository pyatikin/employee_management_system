import React, { useState, useEffect } from 'react';
import axios from 'axios';
import ViewVacancyInWorkDialog from './ViewVacancyInWorkDialog';
import ChangeVacancyStatusDialog from './ChangeVacancyStatusDialog';
import ViewCandidatesInInterviewDialog from './ViewCandidatesInInterview';

function ClosedVacancies({ setPageTitle }) {
    const [closedVacancies, setClosedVacancies] = useState([]);
    const [selectedVacancy, setSelectedVacancy] = useState(null);
    const [isViewVacancyDialogOpen, setViewVacancyDialogOpen] = useState(false);
    const [isChangeStatusDialogOpen, setChangeStatusDialogOpen] = useState(false);
    const [selectedVacancyId, setSelectedVacancyId] = useState(null);
    const [isViewCandidatesDialogOpen, setViewCandidatesDialogOpen] = useState(false);

    useEffect(() => {
        setPageTitle("Закрытые вакансии");
        fetchVacancies();
    }, [setPageTitle]);

    const fetchVacancies = async () => {
        try {
            const response = await axios.get('http://localhost:8080/vacancies/closed');
            setClosedVacancies(response.data);
        } catch (error) {
            console.error('Error fetching closed vacancies:', error);
        }
    };

    const openViewVacancyDialog = vacancy => {
        setSelectedVacancy(vacancy);
        setViewVacancyDialogOpen(true);
    };

    const handleCloseDialog = () => {
        setViewVacancyDialogOpen(false);
        setChangeStatusDialogOpen(false);
        setViewCandidatesDialogOpen(false);
        fetchVacancies();
    };

    const openChangeStatusDialog = vacancyId => {
        setSelectedVacancyId(vacancyId);
        setChangeStatusDialogOpen(true);
    };

    const openCandidatesDialog = vacancy => {
        setSelectedVacancy(vacancy);
        setViewCandidatesDialogOpen(true);
    };

    const renderVacancyRow = vacancy => {
        return (
            <tr key={vacancy.vacancyId}>
                <td>{vacancy.name}</td>
                <td>{vacancy.department ? vacancy.department.name : 'Не указан'}</td>
                <td>{vacancy.salary}</td>
                <td>
                    <button onClick={() => openViewVacancyDialog(vacancy)}>Просмотр</button>
                    <button onClick={() => openChangeStatusDialog(vacancy.vacancyId)}>Изменить статус</button>
                    <button onClick={() => openCandidatesDialog(vacancy)}>Просмотр кандидатов</button>
                </td>
            </tr>
        );
    };

    return (
        <div>
            <h2>Список закрытых вакансий</h2>
            <table>
                <thead>
                <tr>
                    <th>Название</th>
                    <th>Отдел</th>
                    <th>Зарплата</th>
                    <th>Действие</th>
                </tr>
                </thead>
                <tbody>
                {closedVacancies.map(renderVacancyRow)}
                </tbody>
            </table>

            {isViewVacancyDialogOpen && <ViewVacancyInWorkDialog vacancy={selectedVacancy} onClose={handleCloseDialog} />}
            {isChangeStatusDialogOpen && (
                <ChangeVacancyStatusDialog vacancyId={selectedVacancyId} onClose={handleCloseDialog} fetchVacancies={fetchVacancies} />
            )}
            {isViewCandidatesDialogOpen && <ViewCandidatesInInterviewDialog vacancy={selectedVacancy} onClose={handleCloseDialog} />}
        </div>
    );
}

export default ClosedVacancies;
