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
        Department salesDepartment = new Department(3L, "Отдел продаж", "Отдел продаж");

        departmentRepository.save(itDepartment);
        departmentRepository.save(officeDepartment);
        departmentRepository.save(salesDepartment);

        Vacancy itVacancy = new Vacancy(1L, "Инженер-программист", "Разработка новых функций для флагманского продукта компании", 50000.0, LocalDate.parse("2024-04-30"), "1 год", null, null, itDepartment, open, LocalDate.now());
        Vacancy marketingVacancy = new Vacancy(2L, "Маркетолог", "Создание и управление маркетинговыми кампаниями", 45000.0, LocalDate.parse("2024-04-25"), "2 года", null, null, officeDepartment, open, LocalDate.now());
        Vacancy financeVacancy = new Vacancy(3L, "Финансовый аналитик", "Анализ финансовых данных и предоставление инсайтов", 55000.0, LocalDate.parse("2024-05-10"),"1 год", null, null, itDepartment, open, LocalDate.now());

        Vacancy designVacancy = new Vacancy(4L, "Дизайнер", "Разработка дизайна пользовательского интерфейса", 48000.0, LocalDate.parse("2024-04-28"), "2 года", null, null, itDepartment, work, LocalDate.now());
        Vacancy hrVacancy = new Vacancy(5L, "HR-специалист", "Подбор персонала и организация процессов трудоустройства", 46000.0, LocalDate.parse("2024-05-05"), "1 год", null, null, officeDepartment, work, LocalDate.now());
        Vacancy salesVacancy = new Vacancy(6L, "Менеджер по продажам", "Продвижение продуктов компании и увеличение объема продаж", 52000.0, LocalDate.parse("2024-05-15"), "1 год", null, null, salesDepartment, work, LocalDate.now());

        vacancyRepository.save(itVacancy);
        vacancyRepository.save(marketingVacancy);
        vacancyRepository.save(financeVacancy);
        vacancyRepository.save(designVacancy);
        vacancyRepository.save(hrVacancy);
        vacancyRepository.save(salesVacancy);

        // Добавление тестовых данных для кандидатов, скиллов и резюме
        Candidate candidate1 = new Candidate();
        candidate1.setFirstName("Иван");
        candidate1.setLastName("Иванов");
        candidate1.setEmail("ivan@example.com");

        Resume resume1 = new Resume();
        resume1.setPosition("Java Разработчик");
        resume1.setContent("Опытный Java Разработчик с глубокими знаниями Spring Framework. " +
                "Работал над несколькими проектами на корпоративном уровне. Владеет разработкой RESTful API " +
                "и управлением баз данных с использованием SQL и NoSQL баз данных.");
        resumeRepository.save(resume1);

        Skills skills1 = new Skills();
        skills1.setName("Java");
        skills1.setDescription("Владение языком программирования Java. Опыт работы с Spring Framework " +
                "для разработки бэкенд приложений. Знаком с Hibernate ORM для взаимодействия с базами данных.");
        skillsRepository.save(skills1);

        candidate1.getResumes().add(resume1);
        candidate1.getSkills().add(skills1);

        candidateRepository.save(candidate1);

        Candidate candidate2 = new Candidate();
        candidate2.setFirstName("Петр");
        candidate2.setLastName("Петров");
        candidate2.setEmail("petr@example.com");

        Resume resume2 = new Resume();
        resume2.setPosition("Фронтенд Разработчик");
        resume2.setContent("Опытный Фронтенд Разработчик с экспертизой в React.js и Vue.js. " +
                "Работал над различными веб-приложениями от интернет-магазинов до социальных сетей. " +
                "Владеет навыками адаптивного дизайна и принципами UI/UX.");
        resumeRepository.save(resume2);

        Skills skills2 = new Skills();
        skills2.setName("React.js");
        skills2.setDescription("Владение фреймворком React.js. Опыт работы со стейт-менеджментом " +
                "с использованием Redux и context API. Знание компонентной архитектуры.");
        skillsRepository.save(skills2);

        candidate2.getResumes().add(resume2);
        candidate2.getSkills().add(skills2);

        candidateRepository.save(candidate2);

        Candidate candidate3 = new Candidate();
        candidate3.setFirstName("Анна");
        candidate3.setLastName("Сидорова");
        candidate3.setEmail("anna@example.com");

        Resume resume3 = new Resume();
        resume3.setPosition("Специалист по Обработке Данных");
        resume3.setContent("Опытный Специалист по Обработке Данных с экспертизой в машинном обучении и анализе данных. " +
                "Работал над различными проектами, включая прогнозирование, обработку естественного языка " +
                "и компьютерное зрение. Владеет языком программирования Python и библиотеками, такими как " +
                "TensorFlow, PyTorch и scikit-learn.");
        resumeRepository.save(resume3);

        Skills skills3 = new Skills();
        skills3.setName("Машинное Обучение");
        skills3.setDescription("Владение алгоритмами и методиками машинного обучения. " +
                "Опыт работы с инженерией признаков, оценкой моделей и настройкой гиперпараметров. " +
                "Знание глубоких архитектур и нейронных сетей.");
        skillsRepository.save(skills3);

        candidate3.getResumes().add(resume3);
        candidate3.getSkills().add(skills3);

        candidateRepository.save(candidate3);

    }


}
