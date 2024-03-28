package com.pyatkin.is;

import com.pyatkin.is.models.Candidate;
import com.pyatkin.is.models.Department;
import com.pyatkin.is.models.HiringStage;
import com.pyatkin.is.models.Resume;
import com.pyatkin.is.models.Skills;
import com.pyatkin.is.models.Vacancy;
import com.pyatkin.is.repository.CandidateRepository;
import com.pyatkin.is.repository.DepartmentRepository;
import com.pyatkin.is.repository.HiringStageRepository;
import com.pyatkin.is.repository.ResumeRepository;
import com.pyatkin.is.repository.SkillsRepository;
import com.pyatkin.is.repository.VacancyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final VacancyRepository vacancyRepository;
    private final DepartmentRepository departmentRepository;
    private final HiringStageRepository hiringStageRepository;
    private final CandidateRepository candidateRepository;
    private final ResumeRepository resumeRepository;
    private final SkillsRepository skillsRepository;

    public DataLoader(VacancyRepository vacancyRepository, DepartmentRepository departmentRepository, HiringStageRepository hiringStageRepository, CandidateRepository candidateRepository, ResumeRepository resumeRepository, SkillsRepository skillsRepository) {
        this.vacancyRepository = vacancyRepository;
        this.departmentRepository = departmentRepository;
        this.hiringStageRepository = hiringStageRepository;
        this.candidateRepository = candidateRepository;
        this.resumeRepository = resumeRepository;
        this.skillsRepository = skillsRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        HiringStage open = new HiringStage(1L, "открыта");
        HiringStage work = new HiringStage(2L, "в работе");
        hiringStageRepository.save(open);
        hiringStageRepository.save(work);

        Department itDepartment = new Department(1L, "ИТ", "ИТ");
        Department officeDepartment = new Department(2L, "Офис", "Офис");

        departmentRepository.save(itDepartment);
        departmentRepository.save(officeDepartment);

        Vacancy itVacancy = new Vacancy(1L, "Инженер-программист", "Разработка новых функций для флагманского продукта компании", 50000.0, LocalDate.parse("2024-04-30"), null, null, itDepartment, open);
        Vacancy marketingVacancy = new Vacancy(2L, "Маркетолог", "Создание и управление маркетинговыми кампаниями", 45000.0, LocalDate.parse("2024-04-25"), null, null, officeDepartment, open);
        Vacancy financeVacancy = new Vacancy(3L, "Финансовый аналитик", "Анализ финансовых данных и предоставление инсайтов", 55000.0, LocalDate.parse("2024-05-10"), null, null, itDepartment, open);

        vacancyRepository.save(itVacancy);
        vacancyRepository.save(marketingVacancy);
        vacancyRepository.save(financeVacancy);

        // Добавление тестовых данных для кандидатов, скиллов и резюме
        Candidate candidate = new Candidate();
        candidate.setFirstName("Иван");
        candidate.setLastName("Иванов");
        candidate.setEmail("ivan@example.com");

        Resume resume = new Resume();
        resume.setPosition("Java Developer");
        resume.setContent("Experienced Java Developer with strong knowledge of Spring Framework.");
        resumeRepository.save(resume);

        Skills skills = new Skills();
        skills.setName("Java");
        skills.setDescription("Proficient in Java programming language.");
        skillsRepository.save(skills);

        candidate.getResumes().add(resume);
        candidate.getSkills().add(skills);

        candidateRepository.save(candidate);
    }

}
