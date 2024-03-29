import React, { useState, useEffect } from 'react';
import axios from 'axios';

function SkillSelector({ onSelect }) {
    const [skills, setSkills] = useState([]);

    useEffect(() => {
        fetchSkills();
    }, []);

    const fetchSkills = async () => {
        try {
            const response = await axios.get('http://localhost:8080/skills');
            setSkills(response.data);
        } catch (error) {
            console.error('Error fetching skills:', error);
        }
    };

    const handleSkillChange = (e) => {
        const selectedOptions = Array.from(e.target.selectedOptions, option => parseInt(option.value));
        onSelect(selectedOptions);
    };

    return (
        <select multiple onChange={handleSkillChange}>
            {skills.map(skill => (
                <option key={skill.skillsId} value={skill.skillsId}>{skill.name}</option>
            ))}
        </select>
    );
}

export default SkillSelector;
