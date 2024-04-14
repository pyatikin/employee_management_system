import React from 'react';

function Resumes({ resumes }) {
    return (
        <ul>
            {resumes.map(resume => (
                <li key={resume.resumeId}>{resume.position}</li>
            ))}
        </ul>
    );
}

export default Resumes;
