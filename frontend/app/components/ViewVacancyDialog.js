import React from 'react';

function ViewVacancyDialog({ vacancy, onClose }) {
    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <h2>Просмотр карточки вакансии</h2>
                <p>Название: {vacancy.name}</p>
                <p>Отдел: {vacancy.department.name}</p>
                <p>Зарплата: {vacancy.salary}</p>
                <button onClick={onClose}>Закрыть</button>
            </div>
        </div>
    );
}

export default ViewVacancyDialog;
