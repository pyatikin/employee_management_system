import React from 'react';

function Skills({ skills }) {
    return (
        <ul>
            {skills.map(skill => (
                <li key={skill.skillsId}>{skill.name}</li>
            ))}
        </ul>
    );
}

export default Skills;
