import React from 'react';

function CandidateCard({ candidate, onClose }) {
    return (
        <div className="candidate-card-modal">
            <h3>{candidate.firstName} {candidate.lastName}</h3>
            <p><strong>Email:</strong> {candidate.email}</p>
            {/* Дополнительные поля карточки кандидата */}
            <button onClick={onClose}>Закрыть</button>
        </div>
    );
}

export default CandidateCard;
