import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Select from 'react-select';
import Modal from 'react-modal';
import CandidateCard from './CandidateCard';
import AddCandidateForm from './AddCandidateForm';

function CandidatePage({ setPageTitle }) {
    const [candidates, setCandidates] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [skillsOptions, setSkillsOptions] = useState([]);
    const [selectedSkills, setSelectedSkills] = useState([]);
    const [selectedCandidate, setSelectedCandidate] = useState(null);
    const [isAddCandidateModalOpen, setAddCandidateModalOpen] = useState(false);

    useEffect(() => {
        setPageTitle("Соискатели");
        fetchSkillsOptions();
        fetchCandidates();
    }, [setPageTitle]);

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

    const toggleAddCandidateModal = () => {
        setAddCandidateModalOpen(!isAddCandidateModalOpen);
    };

    const considerCandidate = (candidate) => {
        console.log('Рассмотрение кандидата:', candidate);
    };

    const filteredCandidates = candidates.filter(candidate => {
        const fullName = `${candidate.firstName} ${candidate.lastName}`.toLowerCase();
        const matchesName = fullName.includes(searchTerm.toLowerCase());

        // Фильтрация по навыкам
        const selectedSkillIds = selectedSkills.map(skill => skill.value);
        const candidateSkillIds = candidate.skills.map(skill => skill.skillsId);

        // Логическое "И" для навыков
        const matchesSkills = selectedSkillIds.every(skillId => candidateSkillIds.includes(skillId));

        return matchesName && matchesSkills;
    });

    return (
        <div>
            <h2>Соискатели</h2>
            <button onClick={toggleAddCandidateModal}>Добавить кандидата</button>
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
                {selectedCandidate && <CandidateCard candidate={selectedCandidate} onClose={closeCandidateCard} onConsider={considerCandidate} />}
            </Modal>
            <Modal
                isOpen={isAddCandidateModalOpen}
                onRequestClose={toggleAddCandidateModal}
                contentLabel="Добавление кандидата"
            >
                <AddCandidateForm onClose={toggleAddCandidateModal} fetchCandidates={fetchCandidates} />
            </Modal>
        </div>
    );
}

export default CandidatePage;
