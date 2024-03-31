import React, { useState, useEffect } from 'react';
import axios from 'axios';
import ViewVacancyInWorkDialog from './ViewVacancyInWorkDialog';
import ChangeVacancyStatusDialog from './ChangeVacancyStatusDialog';

function WorkingVacancies({ setPageTitle }) {
    const [vacancies, setVacancies] = useState([]);
    const [selectedVacancy, setSelectedVacancy] = useState(null);
    const [isViewDialogOpen, setViewDialogOpen] = useState(false);
    const [isChangeStatusDialogOpen, setChangeStatusDialogOpen] = useState(false);
    const [selectedVacancyId, setSelectedVacancyId] = useState(null);

    useEffect(() => {
        setPageTitle("Вакансии в работе");
        fetchVacancies();
    }, [setPageTitle]);

    const fetchVacancies = async () => {
        try {
            const response = await axios.get('http://localhost:8080/vacancies/working-vacancies');
            setVacancies(response.data);
        } catch (error) {
            console.error('Error fetching working vacancies:', error);
        }
    };

    const openViewDialog = vacancy => {
        setSelectedVacancy(vacancy);
        setViewDialogOpen(true);
    };


    const handleCloseDialog = () => {
        setViewDialogOpen(false);
        setChangeStatusDialogOpen(false);
        fetchVacancies();
    };

    const openChangeStatusDialog = vacancyId => {
        setSelectedVacancyId(vacancyId);
        setChangeStatusDialogOpen(true);
    };

    const renderVacancyRow = vacancy => {
        return (
            <tr key={vacancy.vacancyId}>
                <td>{vacancy.name}</td>
                <td>{vacancy.department.name}</td>
                <td>{vacancy.salary}</td>
                <td>
                    <button onClick={() => openViewDialog(vacancy)}>Просмотр</button>
                    <button onClick={() => openChangeStatusDialog(vacancy.vacancyId)}>Изменить статус</button>
                </td>
            </tr>
        );
    };

    return (
        <div>
            <h2>Список вакансий в работе</h2>
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
                {vacancies.map(renderVacancyRow)}
                </tbody>
            </table>

            {isViewDialogOpen && <ViewVacancyInWorkDialog vacancy={selectedVacancy} onClose={handleCloseDialog} />}
            {isChangeStatusDialogOpen && (
                <ChangeVacancyStatusDialog vacancyId={selectedVacancyId} onClose={handleCloseDialog} fetchVacancies={fetchVacancies} />
            )}
        </div>
    );
}

export default WorkingVacancies;
