import React, { useState } from 'react';
import axios from 'axios';

function AddCandidateForm({ onClose, fetchCandidates }) {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            // Отправляем данные нового кандидата на сервер
            await axios.post('http://localhost:8080/candidates', {
                firstName,
                lastName,
                email
            });
            // Обновляем список кандидатов после успешного добавления
            fetchCandidates();
            // Закрываем модальное окно после успешного добавления
            onClose();
        } catch (error) {
            console.error('Error adding candidate:', error);
        }
    };

    return (
        <div className="candidate-card-modal">
            <h2>Добавить кандидата</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    Имя:
                    <input type="text" value={firstName} onChange={(e) => setFirstName(e.target.value)} required />
                </label>
                <label>
                    Фамилия:
                    <input type="text" value={lastName} onChange={(e) => setLastName(e.target.value)} required />
                </label>
                <label>
                    Email:
                    <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                </label>
                <button type="submit">Добавить</button>
                <button type="button" onClick={onClose}>Отмена</button> {/* Кнопка для отмены добавления кандидата */}
            </form>
        </div>
    );
}

export default AddCandidateForm;
