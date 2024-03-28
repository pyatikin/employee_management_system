import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import ViewVacancyDialog from './ViewVacancyDialog';
import CreateVacancyDialog from './CreateVacancyDialog';

function OpenVacancies({ setPageTitle }) {
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
            const response = await axios.get('http://localhost:8080/vacancies');
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
        fetchVacancies();
    };

    const deleteVacancy = async (vacancyId) => {
        try {
            await axios.delete(`http://localhost:8080/vacancies/${vacancyId}`);
            fetchVacancies();
        } catch (error) {
            console.error('Error deleting vacancy:', error);
        }
    };

    const takeInWork = async (vacancyId) => {
        try {
            await axios.put(`http://localhost:8080/vacancies/${vacancyId}/takeInWork`);
            fetchVacancies(); // Обновляем список вакансий после изменения статуса
        } catch (error) {
            console.error('Error taking vacancy in work:', error);
        }
    };

    const renderVacancyRow = vacancy => {
        return (
            <tr key={vacancy.vacancyId}>
                <td>{vacancy.name}</td>
                <td>{vacancy.department.name}</td>
                <td>{vacancy.salary}</td>
                <td>
                    <button onClick={() => openViewDialog(vacancy)}>Просмотр</button>
                    <button onClick={() => deleteVacancy(vacancy.vacancyId)}>Удалить</button>
                    <button onClick={() => takeInWork(vacancy.vacancyId)}>Взять в работу</button>
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

export default OpenVacancies;
