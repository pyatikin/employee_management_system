import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import ViewVacancyDialog from './ViewVacancyDialog';
import CreateVacancyDialog from './CreateVacancyDialog';

function VacancyPage({ setPageTitle }) {
    const [vacancies, setVacancies] = useState([]);
    const [selectedVacancy, setSelectedVacancy] = useState(null);
    const [isViewDialogOpen, setViewDialogOpen] = useState(false);
    const [isCreateDialogOpen, setCreateDialogOpen] = useState(false);

    useEffect(() => {
        setPageTitle("Вакансии");
        fetchVacancies();
    }, [setPageTitle]);

    const fetchVacancies = async () => {
        try {
            const response = await axios.get('http://localhost:8080/vacancies'); // Ваш URL для запроса данных о вакансиях
            setVacancies(response.data);
        } catch (error) {
            console.error('Error fetching vacancies:', error);
        }
    };

    const openViewDialog = vacancy => {
        setSelectedVacancy(vacancy);
        setViewDialogOpen(true);
    };

    const openCreateDialog = () => {
        setCreateDialogOpen(true);
    };

    const handleCloseDialog = () => {
        setViewDialogOpen(false);
        setCreateDialogOpen(false);
        fetchVacancies(); // Обновляем список вакансий после закрытия диалогового окна
    };

    const renderVacancyRow = vacancy => {
        return (
            <tr key={vacancy.vacancyId}>
                <td>{vacancy.name}</td>
                <td>{vacancy.department.name}</td>
                <td>{vacancy.salary}</td>
                <td>
                    <button onClick={() => openViewDialog(vacancy)}>Просмотр карточки вакансии</button>
                </td>
            </tr>
        );
    };

    return (
        <div>
            <h2>Список вакансий</h2>
            <Link to="#" onClick={openCreateDialog}>Создать новую вакансию</Link>
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

            {isViewDialogOpen && <ViewVacancyDialog vacancy={selectedVacancy} onClose={handleCloseDialog} />}
            {isCreateDialogOpen && <CreateVacancyDialog onCreate={handleCloseDialog} onClose={handleCloseDialog} />}
        </div>
    );
}

export default VacancyPage;
