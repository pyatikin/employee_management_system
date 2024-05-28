package com.pyatkin.is;

import com.pyatkin.is.models.*;
import com.pyatkin.is.repository.*;
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
    private final InterviewRepository interviewRepository;

    public DataLoader(VacancyRepository vacancyRepository, DepartmentRepository departmentRepository, HiringStageRepository hiringStageRepository, CandidateRepository candidateRepository, ResumeRepository resumeRepository, SkillsRepository skillsRepository, InterviewRepository interviewRepository) {
        this.vacancyRepository = vacancyRepository;
        this.departmentRepository = departmentRepository;
        this.hiringStageRepository = hiringStageRepository;
        this.candidateRepository = candidateRepository;
        this.resumeRepository = resumeRepository;
        this.skillsRepository = skillsRepository;
        this.interviewRepository = interviewRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        HiringStage open = new HiringStage(1L, "открыта");
        HiringStage work = new HiringStage(2L, "в работе");
        HiringStage closed = new HiringStage(3L, "закрыта");
        hiringStageRepository.save(open);
        hiringStageRepository.save(work);
        hiringStageRepository.save(closed);

        Department itDepartment = new Department(1L, "ИТ", "ИТ");
        Department officeDepartment = new Department(2L, "Офис", "Офис");
        Department salesDepartment = new Department(3L, "Отдел продаж", "Отдел продаж");

        departmentRepository.save(itDepartment);
        departmentRepository.save(officeDepartment);
        departmentRepository.save(salesDepartment);

        Vacancy itVacancy = new Vacancy(1L, "Инженер-программист", "Разработка новых функций для флагманского продукта компании", 50000.0, LocalDate.parse("2024-04-30"), "1 год", null, null, itDepartment, open, null, null, null);
        Vacancy marketingVacancy = new Vacancy(2L, "Маркетолог", "Создание и управление маркетинговыми кампаниями", 45000.0, LocalDate.parse("2024-04-25"), "2 года", null, null, officeDepartment, open, null, null, null);
        Vacancy financeVacancy = new Vacancy(3L, "Финансовый аналитик", "Анализ финансовых данных и предоставление инсайтов", 55000.0, LocalDate.parse("2024-05-10"), "1 год", null, null, itDepartment, open, null, null, null);

        Vacancy designVacancy = new Vacancy(4L, "Дизайнер", "Разработка дизайна пользовательского интерфейса", 48000.0, LocalDate.parse("2024-04-28"), "2 года", null, null, itDepartment, work, LocalDate.now(), null, null);
        Vacancy hrVacancy = new Vacancy(5L, "HR-специалист", "Подбор персонала и организация процессов трудоустройства", 46000.0, LocalDate.parse("2024-05-05"), "1 год", null, null, officeDepartment, work, LocalDate.now(), null, null);
        Vacancy salesVacancy = new Vacancy(6L, "Менеджер по продажам", "Продвижение продуктов компании и увеличение объема продаж", 52000.0, LocalDate.parse("2024-05-15"), "1 год", null, null, salesDepartment, work, LocalDate.now(), null, null);

        Vacancy closedVacancy1 = new Vacancy(7L, "Аналитик данных", "Анализ данных и создание отчетов", 53000.0, LocalDate.parse("2024-03-10"), "1 год", null, null, itDepartment, closed, LocalDate.parse("2023-05-10"), LocalDate.parse("2023-05-15"), null);
        Vacancy closedVacancy2 = new Vacancy(8L, "Менеджер проектов", "Управление проектами и контроль выполнения задач", 57000.0, LocalDate.parse("2024-02-15"), "3 года", null, null, officeDepartment, closed, LocalDate.parse("2023-04-15"), LocalDate.parse("2023-05-20"), null);
        Vacancy closedVacancy3 = new Vacancy(9L, "Специалист по поддержке", "Обслуживание клиентов и решение их проблем", 44000.0, LocalDate.parse("2024-01-20"), "2 года", null, null, salesDepartment, closed, LocalDate.parse("2023-03-20"), LocalDate.parse("2023-05-22"), null);

        vacancyRepository.save(itVacancy);
        vacancyRepository.save(marketingVacancy);
        vacancyRepository.save(financeVacancy);
        vacancyRepository.save(designVacancy);
        vacancyRepository.save(hrVacancy);
        vacancyRepository.save(salesVacancy);
        vacancyRepository.save(closedVacancy1);
        vacancyRepository.save(closedVacancy2);
        vacancyRepository.save(closedVacancy3);

        // Добавление кандидатов с дополнительными полями
        createCandidateWithDetails("Иван", "Иванов", "ivan@example.com", "Java Разработчик", "Опытный Java Разработчик с глубокими знаниями Spring Framework. Работал над несколькими проектами на корпоративном уровне. Владеет разработкой RESTful API и управлением баз данных с использованием SQL и NoSQL баз данных.", "Мужской", "Россия", "Высшее", "+79001234567", "В поиске", "Java", "Владение языком программирования Java. Опыт работы с Spring Framework для разработки бэкенд приложений. Знаком с Hibernate ORM для взаимодействия с базами данных.");

        createCandidateWithDetails("Петр", "Петров", "petr@example.com", "Фронтенд Разработчик", "Опытный Фронтенд Разработчик с экспертизой в React.js и Vue.js. Работал над различными веб-приложениями от интернет-магазинов до социальных сетей. Владеет навыками адаптивного дизайна и принципами UI/UX.", "Мужской", "Россия", "Высшее", "+79002345678", "В поиске", "React.js", "Владение фреймворком React.js. Опыт работы со стейт-менеджментом с использованием Redux и context API. Знание компонентной архитектуры.");

        createCandidateWithDetails("Анна", "Сидорова", "anna@example.com", "Специалист по Обработке Данных", "Опытный Специалист по Обработке Данных с экспертизой в машинном обучении и анализе данных. Работал над различными проектами, включая прогнозирование, обработку естественного языка и компьютерное зрение. Владеет языком программирования Python и библиотеками, такими как TensorFlow, PyTorch и scikit-learn.", "Женский", "Россия", "Высшее", "+79003456789", "В поиске", "Машинное Обучение", "Владение алгоритмами и методиками машинного обучения. Опыт работы с инженерией признаков, оценкой моделей и настройкой гиперпараметров. Знание глубоких архитектур и нейронных сетей.");

        createCandidateWithDetails("Мария", "Кузнецова", "maria@example.com", "HR-специалист", "Опытный HR-специалист с навыками в подборе персонала и организации трудовых процессов. Владеет методиками оценки кандидатов и управления персоналом.", "Женский", "Россия", "Высшее", "+79004567890", "В поиске", "Управление персоналом", "Навыки управления персоналом, оценки кандидатов, ведения собеседований и адаптации новых сотрудников.");

        createCandidateWithDetails("Алексей", "Смирнов", "alexey@example.com", "Менеджер по продажам", "Опытный менеджер по продажам с навыками в увеличении объемов продаж и продвижении продуктов. Владеет техниками продаж и ведения переговоров.", "Мужской", "Россия", "Высшее", "+79005678901", "В поиске", "Продажи", "Навыки ведения переговоров, техники продаж, управление клиентской базой и разработка стратегий продаж.");

        createCandidateWithDetails("Екатерина", "Михайлова", "ekaterina@example.com", "Финансовый аналитик", "Опытный финансовый аналитик с навыками в анализе финансовых данных и предоставлении инсайтов. Владеет методами финансового моделирования и прогнозирования.", "Женский", "Россия", "Высшее", "+79006789012", "В поиске", "Финансовый анализ", "Навыки анализа финансовых данных, финансового моделирования, прогнозирования и оценки рисков.");

        // Дополнительные кандидаты и навыки
        createCandidateWithDetails("Сергей", "Новиков", "sergey@example.com", "Дизайнер", "Опытный дизайнер с навыками в создании UI/UX дизайнов. Работал над проектами различного масштаба, от мобильных приложений до веб-платформ.", "Мужской", "Россия", "Высшее", "+79007890123", "В поиске", "UI/UX дизайн", "Навыки создания пользовательских интерфейсов и опыта, работа с инструментами дизайна, такими как Figma и Adobe XD.");

        createCandidateWithDetails("Наталья", "Волкова", "natalia@example.com", "Маркетолог", "Опытный маркетолог с навыками в создании и управлении маркетинговыми кампаниями. Работала над проектами в различных отраслях, включая B2B и B2C.", "Женский", "Россия", "Высшее", "+79008901234", "В поиске", "Маркетинг", "Навыки создания маркетинговых стратегий, работа с инструментами аналитики, такими как Google Analytics и Яндекс.Метрика.");

        // Создание интервью для кандидатов для всех вакансий
        for (long i = 1; i <= 8; i++) {
            for (long j = 4; j <= 9; j++) {
                createInterview(candidateRepository.findById(i).orElse(null), vacancyRepository.findById(j).orElse(null), 3 + (int) (Math.random() * 3), 3 + (int) (Math.random() * 3), 3 + (int) (Math.random() * 3), "Отзыв о кандидате");
            }
        }
    }

    private void createCandidateWithDetails(String firstName, String lastName, String email, String position, String resumeContent, String gender, String nationality, String education, String phone, String isLookingForJob, String skillName, String skillDescription) {
        Candidate candidate = new Candidate();
        candidate.setFirstName(firstName);
        candidate.setLastName(lastName);
        candidate.setEmail(email);
        candidate.setGender(gender);
        candidate.setNationality(nationality);
        candidate.setEducation(education);
        candidate.setPhone(phone);
        candidate.setSearchStatus(isLookingForJob);

        Resume resume = new Resume();
        resume.setPosition(position);
        resume.setContent(resumeContent);
        resumeRepository.save(resume);

        Skills skills = new Skills();
        skills.setName(skillName);
        skills.setDescription(skillDescription);
        skillsRepository.save(skills);

        candidate.getResumes().add(resume);
        candidate.getSkills().add(skills);

        candidateRepository.save(candidate);
    }

    private void createInterview(Candidate candidate, Vacancy vacancy, int interviewRating, int conversationRating, int recomendationRating, String feedback) {
        if (candidate != null && vacancy != null) {
            Interview interview = new Interview();
            interview.setCandidate(candidate);
            interview.setVacancy(vacancy);
            interview.setInterviewEvaluation(interviewRating);
            interview.setConversationEvaluation(conversationRating);
            interview.setRecommendationEvaluation(recomendationRating);
            interview.setComment(feedback);
            interviewRepository.save(interview);
        }
    }
}
