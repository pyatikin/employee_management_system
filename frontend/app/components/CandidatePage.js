import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Select from 'react-select';
import Modal from 'react-modal';
import CandidateCard from './CandidateCard';

function CandidatePage({ setPageTitle }) {
    const [candidates, setCandidates] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [skillsOptions, setSkillsOptions] = useState([]);
    const [selectedSkills, setSelectedSkills] = useState([]);
    const [selectedCandidate, setSelectedCandidate] = useState(null);

    useEffect(() => {
        setPageTitle("Соискатели");
        fetchSkillsOptions();
        fetchCandidates();
    }, [setPageTitle]);

    useEffect(() => {
        fetchCandidates();
    }, [selectedSkills]);

    const fetchCandidates = async () => {
        try {
            const skillIds = selectedSkills.map(skill => skill.value);
            const response = await axios.get('http://localhost:8080/candidates', {
                params: {
                    skillIds: skillIds.join(',')
                }
            });
            setCandidates(response.data);
        } catch (error) {
            console.error('Error fetching candidates:', error);
        }
    };

    const handleSearchChange = (e) => {
        setSearchTerm(e.target.value);
    };

    const handleSkillsChange = (selectedOptions) => {
        setSelectedSkills(selectedOptions);
    };

    const fetchSkillsOptions = async () => {
        try {
            const response = await axios.get('http://localhost:8080/skills');
            const skillsData = response.data.map(skill => ({ value: skill.skillsId, label: skill.name }));
            setSkillsOptions(skillsData);
        } catch (error) {
            console.error('Error fetching skills:', error);
        }
    };

    const openCandidateCard = (candidate) => {
        setSelectedCandidate(candidate);
    };

    const closeCandidateCard = () => {
        setSelectedCandidate(null);
    };

    const filteredCandidates = candidates.filter(candidate => {
        const fullName = `${candidate.firstName} ${candidate.lastName}`.toLowerCase();
        return fullName.includes(searchTerm.toLowerCase());
    });

    return (
        <div>
            <h2>Соискатели</h2>
            <input
                type="text"
                placeholder="Поиск по имени"
                value={searchTerm}
                onChange={handleSearchChange}
            />
            <Select
                isMulti
                placeholder="Выберите навыки"
                options={skillsOptions}
                value={selectedSkills}
                onChange={handleSkillsChange}
            />
            <table>
                <thead>
                <tr>
                    <th>Имя</th>
                    <th>Фамилия</th>
                    <th>Email</th>
                </tr>
                </thead>
                <tbody>
                {filteredCandidates.map(candidate => (
                    <tr key={candidate.candidateId} onClick={() => openCandidateCard(candidate)} style={{ cursor: 'pointer' }}>
                        <td>{candidate.firstName}</td>
                        <td>{candidate.lastName}</td>
                        <td>{candidate.email}</td>
                    </tr>
                ))}
                </tbody>
            </table>
            <Modal
                isOpen={selectedCandidate !== null}
                onRequestClose={closeCandidateCard}
                contentLabel="Карточка кандидата"
            >
                {selectedCandidate && <CandidateCard candidate={selectedCandidate} onClose={closeCandidateCard} />}
            </Modal>
        </div>
    );
}

export default CandidatePage;
