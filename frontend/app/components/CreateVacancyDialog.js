import React, { useState, useEffect } from 'react';
import axios from 'axios';

function CreateVacancyDialog({ onCreate, onClose, updateVacancies }) {
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [salary, setSalary] = useState('');
    const [hiringDeadline, setHiringDeadline] = useState('');
    const [departmentId, setDepartmentId] = useState('');
    const [departments, setDepartments] = useState([]);

    useEffect(() => {
        const fetchDepartments = async () => {
            try {
                const response = await axios.get('http://localhost:8080/departments'); // Замените на ваш URL для получения списка департаментов
                setDepartments(response.data);
            } catch (error) {
                console.error('Error fetching departments:', error);
            }
        };

        fetchDepartments();
    }, []);

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/vacancies', {
                name,
                description,
                salary,
                hiringDeadline,
                departmentId // Передаем сразу идентификатор департамента
            });
            onCreate(response.data);
            onClose(); // Закрываем форму после успешного создания вакансии
            updateVacancies(); // Обновляем список вакансий
        } catch (error) {
            console.error('Error creating vacancy:', error);
        }
    };

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <h2>Создать новую вакансию</h2>
                <form onSubmit={handleSubmit} className="create-vacancy-form">
                    <label htmlFor="name">Название:</label>
                    <input type="text" id="name" value={name} onChange={(e) => setName(e.target.value)} required />

                    <label htmlFor="description">Описание:</label>
                    <textarea id="description" value={description} onChange={(e) => setDescription(e.target.value)} required />

                    <label htmlFor="salary">Зарплата:</label>
                    <input type="number" id="salary" value={salary} onChange={(e) => setSalary(e.target.value)} required />

                    <label htmlFor="hiringDeadline">Крайний срок:</label>
                    <input type="date" id="hiringDeadline" value={hiringDeadline} onChange={(e) => setHiringDeadline(e.target.value)} required />

                    <label htmlFor="department">Отдел:</label>
                    <select id="department" value={departmentId} onChange={(e) => setDepartmentId(e.target.value)} required>
                        <option value="">Выберите отдел</option>
                        {departments.map(department => (
                            <option key={department.departmentId} value={department.departmentId}>{department.name}</option>
                        ))}
                    </select>

                    <div className="button-group">
                        <button type="submit" className="create-button">Создать</button>
                        <button type="button" onClick={onClose} className="cancel-button">Отмена</button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default CreateVacancyDialog;
