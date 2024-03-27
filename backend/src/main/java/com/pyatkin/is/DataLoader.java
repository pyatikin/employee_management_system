package com.pyatkin.is;

import com.pyatkin.is.models.Department;
import com.pyatkin.is.models.Vacancy;
import com.pyatkin.is.repository.DepartmentRepository;
import com.pyatkin.is.repository.VacancyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final VacancyRepository vacancyRepository;
    private final DepartmentRepository departmentRepository;

    public DataLoader(VacancyRepository vacancyRepository, DepartmentRepository departmentRepository) {
        this.vacancyRepository = vacancyRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        Department itDepartment = new Department(1L, "ИТ", "ИТ");
        Department officeDepartment = new Department(2L, "Офис", "Офис");

        this.departmentRepository.save(itDepartment);
        this.departmentRepository.save(officeDepartment);

        this.vacancyRepository.save(new Vacancy(1L, "Инженер-программист", "Разработка новых функций для флагманского продукта компании", 50000.0, LocalDate.parse("2024-04-30"), null, null, itDepartment));
        this.vacancyRepository.save(new Vacancy(2L, "Маркетолог", "Создание и управление маркетинговыми кампаниями", 45000.0, LocalDate.parse("2024-04-25"), null,null, officeDepartment));
        this.vacancyRepository.save(new Vacancy(3L, "Финансовый аналитик", "Анализ финансовых данных и предоставление инсайтов", 55000.0, LocalDate.parse("2024-05-10"), null,null, itDepartment));
    }
}
