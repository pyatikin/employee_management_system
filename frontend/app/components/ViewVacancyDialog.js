import React from 'react';

function ViewVacancyDialog({ vacancy, onClose }) {
    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <span className="close" onClick={onClose}>&times;</span>
                <h2>Просмотр карточки вакансии</h2>
                <p>Название: {vacancy.name}</p>
                <p>Отдел: {vacancy.department.name}</p>
                <p>Зарплата: {vacancy.salary}</p>
                <p>Требуемый опыт: {vacancy.experience}</p>
                <p>Описание: {vacancy.description}</p>
                <p>Дата создания: {vacancy.startDate}</p>
                {/* Добавьте другие поля вакансии здесь */}
            </div>
        </div>
    );
}

export default ViewVacancyDialog;
