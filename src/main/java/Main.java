import dao.DaoImplementation;
import entities.Discipline;
import entities.Role;
import entities.Task;
import entities.User;
import java.util.*;

import static dao.DaoImplementation.*;


public class Main {
    private static DaoImplementation dao = new DaoImplementation();
    public static void main(String[] args) {
        // Populate tables with some data using hibernate
        List<Role> roles = roleFactory();
        dao.toDatabase(roles);
        List <User> users = userFactory(roles);
        dao.toDatabase(users);
        List<Discipline> disciplines = disciplineFactory(users, users);
        dao.toDatabase(disciplines);
        List<Task> tasks = taskFactory(users);
        dao.toDatabase(tasks);


        List<User> userList = dao.getUsersByRole("Tester");
        for (User u : userList) {
            System.out.println(u);
        }

        List<User> userListByDiscipline = dao.userListByDiscipline("Applications Management");
        for (User u : userListByDiscipline) {
            System.out.println(u);
        }

        List<User> userListByTaskStatus = dao.userListByTaskStatus("TO_DO");
        for (User u : userListByTaskStatus) {
            System.out.println(u);
        }

        dao.changeDisciplineHead(disciplines.get(0), userList.get(2));


        List<Discipline> disciplineList = dao.disciplineListLessThan(3);
        for (Discipline d:disciplineList) {
            System.out.println(d.getName());
        }

        dao.deleteObject(userList.get(1));

    }
}