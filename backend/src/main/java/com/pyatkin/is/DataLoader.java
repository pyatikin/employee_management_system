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

        Vacancy itVacancy = new Vacancy(1L, "Инженер-программист", "Разработка новых функций для флагманского продукта компании", 50000.0, LocalDate.parse("2024-04-30"), null, null, itDepartment, open, null);
        Vacancy marketingVacancy = new Vacancy(2L, "Маркетолог", "Создание и управление маркетинговыми кампаниями", 45000.0, LocalDate.parse("2024-04-25"), null, null, officeDepartment, open, null);
        Vacancy financeVacancy = new Vacancy(3L, "Финансовый аналитик", "Анализ финансовых данных и предоставление инсайтов", 55000.0, LocalDate.parse("2024-05-10"), null, null, itDepartment, open, null);
        Vacancy okWork = new Vacancy(4L, "Работа", "нужно работать", 123.0, LocalDate.parse("2024-05-10"), null, null, itDepartment, work, LocalDate.now());
        Vacancy notWork = new Vacancy(5L, "Не работа", "Не нужно работать", 0.0, LocalDate.parse("2024-05-10"), null, null, officeDepartment, work, LocalDate.now());

        vacancyRepository.save(itVacancy);
        vacancyRepository.save(marketingVacancy);
        vacancyRepository.save(financeVacancy);
        vacancyRepository.save(okWork);
        vacancyRepository.save(notWork);

        // Добавление тестовых данных для кандидатов, скиллов и резюме
        Candidate candidate1 = new Candidate();
        candidate1.setFirstName("Иван");
        candidate1.setLastName("Иванов");
        candidate1.setEmail("ivan@example.com");

        Resume resume1 = new Resume();
        resume1.setPosition("Java Developer");
        resume1.setContent("Experienced Java Developer with strong knowledge of Spring Framework.");
        resumeRepository.save(resume1);

        Skills skills1 = new Skills();
        skills1.setName("Java");
        skills1.setDescription("Proficient in Java programming language.");
        skillsRepository.save(skills1);

        candidate1.getResumes().add(resume1);
        candidate1.getSkills().add(skills1);

        candidateRepository.save(candidate1);

        Candidate candidate2 = new Candidate();
        candidate2.setFirstName("Петр");
        candidate2.setLastName("Петров");
        candidate2.setEmail("petr@example.com");

        Resume resume2 = new Resume();
        resume2.setPosition("Frontend Developer");
        resume2.setContent("Skilled Frontend Developer with expertise in React.js and Vue.js.");
        resumeRepository.save(resume2);

        Skills skills2 = new Skills();
        skills2.setName("React.js");
        skills2.setDescription("Proficient in React.js framework.");
        skillsRepository.save(skills2);

        candidate2.getResumes().add(resume2);
        candidate2.getSkills().add(skills2);

        candidateRepository.save(candidate2);

        Candidate candidate3 = new Candidate();
        candidate3.setFirstName("Анна");
        candidate3.setLastName("Сидорова");
        candidate3.setEmail("anna@example.com");

        Resume resume3 = new Resume();
        resume3.setPosition("Data Scientist");
        resume3.setContent("Experienced Data Scientist with expertise in machine learning and data analysis.");
        resumeRepository.save(resume3);

        Skills skills3 = new Skills();
        skills3.setName("Machine Learning");
        skills3.setDescription("Skilled in machine learning algorithms and techniques.");
        skillsRepository.save(skills3);

        candidate3.getResumes().add(resume3);
        candidate3.getSkills().add(skills3);

        candidateRepository.save(candidate3);
    }


}
