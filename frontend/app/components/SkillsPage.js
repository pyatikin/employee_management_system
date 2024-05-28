import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Modal from 'react-modal';
import AddSkillForm from './AddSkillForm'; // Импортируем компонент AddSkillForm

function SkillsPage({ setPageTitle }) {
    const [skills, setSkills] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [isAddSkillModalOpen, setAddSkillModalOpen] = useState(false); // Состояние модального окна для добавления навыка

    useEffect(() => {
        setPageTitle("Навыки");
        fetchSkills();
    }, [setPageTitle]);

    const fetchSkills = async () => {
        try {
            const response = await axios.get('http://localhost:8080/skills');
            setSkills(response.data);
        } catch (error) {
            console.error('Error fetching skills:', error);
        }
    };

    const handleSearchChange = (e) => {
        setSearchTerm(e.target.value);
    };

    const toggleAddSkillModal = () => {
        setAddSkillModalOpen(!isAddSkillModalOpen);
    };

    const handleDeleteSkill = async (skillId) => {
        try {
            await axios.delete(`http://localhost:8080/skills/${skillId}`);
            fetchSkills(); // Обновляем список навыков после удаления
        } catch (error) {
            console.error('Error deleting skill:', error);
        }
    };

    const filteredSkills = skills.filter(skill => skill.name.toLowerCase().includes(searchTerm.toLowerCase()));

    return (
        <div>
            <h2>Навыки</h2>
            <button onClick={toggleAddSkillModal}>Добавить навык</button>
            <input
                type="text"
                placeholder="Поиск по названию навыка"
                value={searchTerm}
                onChange={handleSearchChange}
            />
            <table>
                <thead>
                <tr>
                    <th>Название</th>
                    <th>Описание</th>
                    <th>Действия</th>
                </tr>
                </thead>
                <tbody>
                {filteredSkills.map(skill => (
                    <tr key={skill.skillsId}>
                        <td>{skill.name}</td>
                        <td>{skill.description}</td>
                        <td>
                            <button onClick={() => handleDeleteSkill(skill.skillsId)}>Удалить</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
            <Modal
                isOpen={isAddSkillModalOpen}
                onRequestClose={toggleAddSkillModal}
                contentLabel="Добавление навыка"
            >
                <AddSkillForm onClose={toggleAddSkillModal} fetchSkills={fetchSkills} />
            </Modal>
        </div>
    );
}

export default SkillsPage;
