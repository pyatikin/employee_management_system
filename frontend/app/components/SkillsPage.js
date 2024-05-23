import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Modal from 'react-modal';
import AddSkillForm from './AddSkillForm';


function SkillsPage({ setPageTitle }) {
    const [skills, setSkills] = useState([]);
    const [isAddSkillModalOpen, setAddSkillModalOpen] = useState(false);

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

    const toggleAddSkillModal = () => {
        setAddSkillModalOpen(!isAddSkillModalOpen);
    };

    return (
        <div>
            <h2>Навыки</h2>
            <button onClick={toggleAddSkillModal}>Добавить навык</button>
            <table>
                <thead>
                <tr>
                    <th>Название</th>
                    <th>Описание</th>
                </tr>
                </thead>
                <tbody>
                {skills.map(skill => (
                    <tr key={skill.skillsId}>
                        <td>{skill.name}</td>
                        <td>{skill.description}</td>
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
