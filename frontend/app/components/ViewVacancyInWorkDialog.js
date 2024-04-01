import React from 'react';

function ViewVacancyInWorkDialog({ vacancy, onClose }) {
    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <span className="close" onClick={onClose}>&times;</span>
                <h2>Просмотр карточки вакансии</h2>
                <p>Название: {vacancy.name}</p>
                <p>Отдел: {vacancy.department.name}</p>
                <p>Зарплата: {vacancy.salary}</p>
            </div>
        </div>
    );
}

export default ViewVacancyInWorkDialog;
