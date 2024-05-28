import React, {useState} from 'react';
import axios from 'axios';


function AddSkillForm({onClose, fetchSkills}) {
    const [skillName, setSkillName] = useState('');
    const [skillDescription, setSkillDescription] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            await axios.post('http://localhost:8080/skills', {
                name: skillName,
                description: skillDescription
            }, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            fetchSkills();
            onClose();
        } catch (error) {
            console.error('Error adding skill:', error);
        }
    };

    return (
        <div className="candidate-card-modal-skills">
            <h2>Добавить навык</h2>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Название навыка:</label>
                    <input type="text" value={skillName} onChange={(e) => setSkillName(e.target.value)} required/>
                </div>
                <div className="form-group">
                    <label>Описание навыка:</label>
                    <textarea value={skillDescription} onChange={(e) => setSkillDescription(e.target.value)} required/>
                </div>
                <div className="form-group">
                    <button type="submit">Добавить</button>
                    <button type="button" onClick={onClose}>Отмена</button>
                </div>
            </form>
        </div>
    );
}

export default AddSkillForm;
