import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Select from 'react-select';

function AddCandidateForm({ onClose, fetchCandidates }) {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [selectedSkills, setSelectedSkills] = useState([]);
    const [resumePosition, setResumePosition] = useState('');
    const [resumeContent, setResumeContent] = useState('');
    const [skillsOptions, setSkillsOptions] = useState([]); // Добавляем переменную для хранения опций навыков

    useEffect(() => {
        // Загрузка данных о навыках при монтировании компонента
        fetchSkillsOptions();
    }, []);

    const fetchSkillsOptions = async () => {
        try {
            const response = await axios.get('http://localhost:8080/skills');
            const skillsData = response.data.map(skill => ({ value: skill.skillsId, label: skill.name }));
            setSkillsOptions(skillsData);
        } catch (error) {
            console.error('Error fetching skills:', error);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            // Отправляем данные нового кандидата на сервер
            await axios.post('http://localhost:8080/candidates', {
                firstName,
                lastName,
                email,
                skills: selectedSkills.map(skill => skill.value), // Отправляем только значения выбранных скиллов
                resume: {
                    position: resumePosition,
                    content: resumeContent
                }
            });
            // Обновляем список кандидатов после успешного добавления
            fetchCandidates();
            // Закрываем модальное окно после успешного добавления
            onClose();
        } catch (error) {
            console.error('Error adding candidate:', error);
        }
    };

    const handleSkillsChange = (selectedOptions) => {
        setSelectedSkills(selectedOptions);
    };

    return (
        <div className="candidate-card-modal">
            <h2>Добавить кандидата</h2>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Имя:</label>
                    <input type="text" value={firstName} onChange={(e) => setFirstName(e.target.value)} required />
                </div>
                <div className="form-group">
                    <label>Фамилия:</label>
                    <input type="text" value={lastName} onChange={(e) => setLastName(e.target.value)} required />
                </div>
                <div className="form-group">
                    <label>Email:</label>
                    <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                </div>
                {/* Выбор навыков */}
                <div className="form-group">
                    <label>Выберите навыки:</label>
                    <Select
                        isMulti
                        options={skillsOptions}
                        value={selectedSkills}
                        onChange={handleSkillsChange}
                    />
                </div>
                <div className="form-group">
                    <label>Должность:</label>
                    <input type="text" value={resumePosition} onChange={(e) => setResumePosition(e.target.value)} required />
                </div>
                <div className="form-group">
                    <label>Резюме:</label>
                    <textarea value={resumeContent} onChange={(e) => setResumeContent(e.target.value)} required />
                </div>
                <div className="form-group">
                    <button type="submit">Добавить</button>
                    <button type="button" onClick={onClose}>Отмена</button> {/* Кнопка для отмены добавления кандидата */}
                </div>
            </form>
        </div>
    );
}

export default AddCandidateForm;
