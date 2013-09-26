package wad.tokkel.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.junit.Test;
import fi.helsinki.cs.tmc.edutestutils.Points;

import javax.annotation.Resource;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.web.context.WebApplicationContext;
import wad.tokkel.models.Project;
import wad.tokkel.models.Task;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/spring-base.xml"})
@WebAppConfiguration
public class TokkelRestApiTest {

    @Resource
    private WebApplicationContext waco;
    private MockMvc controllerMock;
    private ObjectMapper mapper;

    @Before
    public void setup() {
        controllerMock = webAppContextSetup(waco).build();
        mapper = new ObjectMapper();
    }

    @Test
    @Points("W4E08.1")
    public void testPostProject() throws Exception {
        String randomProjectName = "project:" + UUID.randomUUID().toString();
        postProject(randomProjectName);
    }

    @Test
    @Points("W4E08.1")
    public void testPostAndGetProject() throws Exception {
        String randomProjectName = "project:" + UUID.randomUUID().toString();
        Project response = postProject(randomProjectName);
        getProject(response.getId(), randomProjectName);
    }

    @Test
    @Points("W4E08.1")
    public void testPostAndGetProjectAndListProjects() throws Exception {
        String randomProjectName = "project:" + UUID.randomUUID().toString();
        Project response = postProject(randomProjectName);
        getProject(response.getId(), randomProjectName);
        expectToFindProjectFromList(response.getId(), randomProjectName);
    }

    @Test
    @Points("W4E08.1")
    public void testPostAndGetProjectAndListProjectsAndDeleteProject() throws Exception {
        String randomProjectName = "project:" + UUID.randomUUID().toString();
        Project response = postProject(randomProjectName);
        getProject(response.getId(), randomProjectName);
        expectToFindProjectFromList(response.getId(), randomProjectName);
        deleteProject(response.getId());
        expectNotToFindProjectFromList(response.getId());
    }

    @Test
    @Points("W4E08.2")
    public void testPostTask() throws Exception {
        String randomTaskName = "task:" + UUID.randomUUID().toString();
        postTask(randomTaskName);
    }

    @Test
    @Points("W4E08.2")
    public void testPostAndGetTask() throws Exception {
        String randomTaskName = "task:" + UUID.randomUUID().toString();
        Task t = postTask(randomTaskName);
        getTask(t.getId(), randomTaskName);
    }

    @Test
    @Points("W4E08.2")
    public void testPostAndGetTaskAndListTask() throws Exception {
        String randomTaskName = "task:" + UUID.randomUUID().toString();
        Task t = postTask(randomTaskName);
        getTask(t.getId(), randomTaskName);
        expectToFindTaskFromList(new int[]{t.getId()}, new String[]{randomTaskName});
    }

    @Test
    @Points("W4E08.2")
    public void testPostAndGetTaskAndListTasksAndDeleteTask() throws Exception {
        String randomTaskName = "task:" + UUID.randomUUID().toString();
        Task t = postTask(randomTaskName);
        getTask(t.getId(), randomTaskName);
        expectToFindTaskFromList(new int[]{t.getId()}, new String[]{randomTaskName});
        deleteTask(t.getId());
        expectNotToFindTaskFromList(new int[]{t.getId()});
    }

    @Test
    @Points("W4E08.3")
    public void testPostProjectAndACoupleOfTasks() throws Exception {
        String randomProjectName = "project:" + UUID.randomUUID().toString();
        Project p = postProject(randomProjectName);
        Integer projectId = p.getId();

        String[] descriptions = new String[3];
        for (int i = 0; i < descriptions.length; i++) {
            descriptions[i] = "description:" + UUID.randomUUID().toString();
        }
        postTasksWithSpecifiedDescription(projectId, descriptions);
    }

    @Test
    @Points("W4E08.3")
    public void testPostProjectAndACoupleOfTasksAndGetTasks() throws Exception {
        String randomProjectName = "project:" + UUID.randomUUID().toString();
        Project p = postProject(randomProjectName);
        Integer projectId = p.getId();

        String[] descriptions = new String[3];
        for (int i = 0; i < descriptions.length; i++) {
            descriptions[i] = "description:" + UUID.randomUUID().toString();
        }
        int[] taskIds = postTasksWithSpecifiedDescription(projectId, descriptions);

        for (int i = 0; i < taskIds.length; i++) {
            getTaskInProject(projectId, taskIds[i], descriptions[i]);
        }
    }

    @Test
    @Points("W4E08.3")
    public void testPostProjectAndACoupleOfTasksAndListTasks() throws Exception {
        String randomProjectName = "project:" + UUID.randomUUID().toString();
        Project p = postProject(randomProjectName);
        Integer projectId = p.getId();

        String[] descriptions = new String[3];
        for (int i = 0; i < descriptions.length; i++) {
            descriptions[i] = "description:" + UUID.randomUUID().toString();
        }
        int[] taskIds = postTasksWithSpecifiedDescription(projectId, descriptions);

        getTasksInProject(projectId);
        expectToFindTaskFromList(projectId, taskIds, descriptions);
    }

    @Test
    @Points("W4E08.3")
    public void testPostProjectAndACoupleOfTasksAndListTasksAndDeleteSpecificTasks() throws Exception {
        String randomProjectName = "project:" + UUID.randomUUID().toString();
        Project p = postProject(randomProjectName);
        Integer projectId = p.getId();

        String[] descriptions = new String[7];
        for (int i = 0; i < descriptions.length; i++) {
            descriptions[i] = "description:" + UUID.randomUUID().toString();
        }
        int[] taskIds = postTasksWithSpecifiedDescription(projectId, descriptions);

        getTasksInProject(projectId);
        expectToFindTaskFromList(projectId, taskIds, descriptions);

        deleteTaskInProject(projectId, taskIds[1]);
        deleteTaskInProject(projectId, taskIds[3]);
        deleteTaskInProject(projectId, taskIds[4]);

        expectNotToFindTaskFromList(projectId, new int[]{taskIds[1], taskIds[3], taskIds[4]});
    }

    @Test
    @Points("W4E08.4")
    public void testPostProjectAndACoupleOfTasksAndStartTasksAndStopTasks() throws Exception {
        String randomProjectName = "project:" + UUID.randomUUID().toString();
        Project p = postProject(randomProjectName);
        Integer projectId = p.getId();

        String[] descriptions = new String[3];
        for (int i = 0; i < descriptions.length; i++) {
            descriptions[i] = "description:" + UUID.randomUUID().toString();
        }
        int[] taskIds = postTasksWithSpecifiedDescription(projectId, descriptions);

        long[] startTimes = new long[descriptions.length];
        for (int i = 0; i < descriptions.length; i++) {
            Task t = startTaskInProject(projectId, taskIds[i], descriptions[i]);
            startTimes[i] = t.getStartedTime().getTime();
        }

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
        }

        long[] stopTimes = new long[descriptions.length];
        for (int i = 0; i < descriptions.length; i++) {
            Task t = stopTaskInProject(projectId, taskIds[i], descriptions[i]);
            stopTimes[i] = t.getStoppedTime().getTime();

            if ((stopTimes[i] - startTimes[i]) < 1) {
                fail("Start time for task was after stop time. Verify that you set start time and stop time for task correctly.");
            }
        }
    }

    public int[] postTasksWithSpecifiedDescription(Integer projectId, String[] descriptions) throws Exception {
        int[] ids = new int[descriptions.length];
        for (int i = 0; i < descriptions.length; i++) {
            Task t = postTaskInProject(projectId, descriptions[i]);
            ids[i] = t.getId();
        }
        return ids;
    }

    protected void expectToFindProjectFromList(Integer expectedId, String expectedName) throws Exception {
        List<Project> projects = getProjects();

        Project projectWithCorrectId = null;
        for (Project project : projects) {
            if (expectedId == project.getId()) {
                projectWithCorrectId = project;
            }
        }

        if (projectWithCorrectId == null) {
            fail("Cannot find a stored project in the JSON array response of GET-request to /app/projects");
            return;
        }

        assertEquals("GET-request to /app/projects returns incorrect name for project",
                expectedName, projectWithCorrectId.getName());
    }

    protected void expectToFindTaskFromList(int[] expectedIds, String[] expectedDescriptions) throws Exception {
        List<Task> tasks = getTasks();

        for (int i = 0; i < expectedIds.length; i++) {
            int expectedId = expectedIds[i];
            String expectedDescription = expectedDescriptions[i];
            boolean foundId = false;

            for (Task task : tasks) {
                if (String.valueOf(expectedId).equals(String.valueOf(task.getId()))) {
                    foundId = true;
                    assertEquals("GET-request to /app/tasks returns incorrect description for a task",
                            expectedDescription, task.getDescription());
                }
            }

            if (!foundId) {
                fail("Cannot find a stored task in the JSON array response of GET-request to /app/tasks");
            }
        }
    }

    protected void expectToFindTaskFromList(Integer projectId, int[] expectedIds, String[] expectedDescriptions) throws Exception {
        List<Task> tasks = getTasksInProject(projectId);

        for (Task task : tasks) {
            Object id = task.getId();
            boolean foundId = false;
            for (int i = 0; i < expectedIds.length; i++) {
                int expectedId = expectedIds[i];
                String expectedDescription = expectedDescriptions[i];
                if (String.valueOf(expectedId).equals(String.valueOf(id))) {
                    foundId = true;
                    assertEquals("GET-request to /app/projects/" + projectId + "/tasks returns incorrect description for a task",
                            expectedDescription, task.getDescription());
                    break;
                }
            }

            if (!foundId) {
                fail("Cannot find a stored task in the JSON array response of GET-request to /app/projects/" + projectId + "/tasks");
            }
        }

    }

    protected void expectNotToFindProjectFromList(Integer expectedId) throws Exception {
        List<Project> projects = getProjects();

        for (Project project : projects) {
            if (String.valueOf(expectedId).equals(String.valueOf(project.getId()))) {
                fail("Expected not to find a project after deletion in the JSON array response of GET-request to /app/projects");
            }
        }
    }

    protected void expectNotToFindTaskFromList(int[] expectedIds) throws Exception {
        List<Task> tasks = getTasks();

        for (int i = 0; i < expectedIds.length; i++) {
            int expectedId = expectedIds[i];
            boolean foundId = false;

            for (Task task : tasks) {
                Object id = task.getId();
                if (String.valueOf(expectedId).equals(String.valueOf(id))) {
                    foundId = true;
                    break;
                }
            }

            if (foundId) {
                fail("Expected not to find a task after deletion in the JSON array response of GET-request to /app/tasks");
            }
        }
    }

    protected void expectNotToFindTaskFromList(Integer projectId, int[] expectedIds) throws Exception {
        List<Task> tasks = getTasksInProject(projectId);

        for (Task task : tasks) {
            Object id = task.getId();
            boolean foundId = false;
            for (int i = 0; i < expectedIds.length; i++) {
                int expectedId = expectedIds[i];
                if (String.valueOf(expectedId).equals(String.valueOf(id))) {
                    foundId = true;
                    break;
                }
            }
            if (foundId) {
                fail("Expected not to find a task after deletion in the JSON array response of GET-request to /app/projects/" + projectId + "/tasks");
            }
        }
    }

    private Project postProject(String name) throws Exception {
        Project project = new Project();
        project.setName(name);
        return postProject(project);
    }

    private Project postProject(Project project) throws Exception {
        MvcResult result = controllerMock
                .perform(post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(project)))
                .andReturn();
        Project p = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Project>() {
        });

        assertNotNull(p);
        assertNotNull(p.getId());
        assertEquals(project.getName(), p.getName());
        assertNotNull(p.getCreationTime());

        return p;
    }

    protected Project getProject(Integer id, String expectedName) throws Exception {
        MvcResult result = controllerMock
                .perform(get("/projects/{id}", id))
                .andReturn();
        Project p = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Project>() {
        });

        assertNotNull(p);
        assertEquals(expectedName, p.getName());
        assertNotNull(p.getCreationTime());

        return p;
    }

    protected void deleteProject(Integer id) throws Exception {
        MvcResult result = controllerMock
                .perform(delete("/projects/{id}", id))
                .andReturn();
    }

    protected List<Project> getProjects() throws Exception {
        MvcResult result = controllerMock.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        return mapper.readValue(content, new TypeReference<List<Project>>() {
        });
    }

    protected Task postTask(String description) throws Exception {
        Task t = new Task();
        t.setDescription(description);
        return postTask(t);
    }

    protected Task postTask(Task task) throws Exception {
        MvcResult result = controllerMock
                .perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(task)))
                .andReturn();
        Task t = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Task>() {
        });

        assertNotNull(t);
        assertNotNull(t.getId());
        assertEquals(task.getDescription(), t.getDescription());
        assertNull(t.getStartedTime());
        assertNull(t.getStoppedTime());
        assertNull(t.getProject());

        return t;
    }

    protected Task getTask(Integer taskId, String expectedDescription) throws Exception {
        MvcResult result = controllerMock
                .perform(get("/tasks/{id}", taskId))
                .andReturn();
        Task t = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Task>() {
        });

        assertNotNull(t);
        assertNotNull(t.getId());
        assertEquals(expectedDescription, t.getDescription());
        assertNull(t.getProject());

        return t;
    }

    protected void deleteTask(Integer taskId) throws Exception {
        MvcResult result = controllerMock
                .perform(delete("/tasks/{id}", taskId))
                .andReturn();
    }

    protected List<Task> getTasks() throws Exception {
        MvcResult result = controllerMock.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        return mapper.readValue(content, new TypeReference<List<Task>>() {
        });
    }

    protected Task postTaskInProject(Integer projectId, String description) throws Exception {
        Task t = new Task();
        t.setDescription(description);
        return postTaskInProject(projectId, t);
    }

    protected Task postTaskInProject(Integer projectId, Task task) throws Exception {
        MvcResult result = controllerMock
                .perform(post("/projects/{projectId}/tasks", projectId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(task)))
                .andReturn();
        Task t = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Task>() {
        });

        assertNotNull(t);
        assertNotNull(t.getId());
        assertEquals(task.getDescription(), t.getDescription());
        assertNull(t.getStartedTime());
        assertNull(t.getStoppedTime());
        assertNull(t.getProject()); // we do not want to have the full project here
        assertEquals(projectId, t.getProjectId()); // but just the project id

        return t;
    }

    protected Task getTaskInProject(Integer projectId,
            Integer taskId, String expectedDescription) throws Exception {
        MvcResult result = controllerMock
                .perform(get("/projects/{projectId}/tasks/{id}", projectId, taskId))
                .andReturn();
        Task t = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Task>() {
        });

        assertNotNull(t);
        assertNotNull(t.getId());
        assertEquals(expectedDescription, t.getDescription());
        assertNull(t.getProject()); // no project in tasks
        assertEquals(projectId, t.getProjectId()); // but yes project id

        return t;
    }

    protected List<Task> getTasksInProject(Integer projectId) throws Exception {
        MvcResult result = controllerMock.perform(get("/projects/{projectId}/tasks", projectId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        return mapper.readValue(content, new TypeReference<List<Task>>() {
        });
    }

    protected void deleteTaskInProject(Integer projectId, Integer taskId) throws Exception {
        MvcResult result = controllerMock
                .perform(delete("/projects/{projectId}/tasks/{id}", projectId, taskId))
                .andReturn();
    }

    protected Task startTaskInProject(Integer projectId, Integer taskId,
            String expectedDescription) throws Exception {
        Task task = getTaskInProject(projectId, taskId, expectedDescription);
        task.setStart(true);

        MvcResult result = controllerMock
                .perform(put("/projects/{projectId}/tasks/{taskId}", projectId, taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(task)))
                .andReturn();
        Task t = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Task>() {
        });


        assertNotNull(t);
        assertNotNull(t.getId());
        assertEquals(expectedDescription, t.getDescription());
        assertNull(t.getProject()); // no project in tasks
        assertEquals(projectId, t.getProjectId()); // but yes project id

        assertNotNull(t.getStartedTime()); // should be started!
        assertNull(t.getStoppedTime());

        return t;
    }

    protected Task stopTaskInProject(Integer projectId, Integer taskId,
            String expectedDescription) throws Exception {
        Task task = getTaskInProject(projectId, taskId, expectedDescription);
        task.setStop(true);

        MvcResult result = controllerMock
                .perform(put("/projects/{projectId}/tasks/{taskId}", projectId, taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(task)))
                .andReturn();
        Task t = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Task>() {
        });


        assertNotNull(t);
        assertNotNull(t.getId());
        assertEquals(expectedDescription, t.getDescription());
        assertNull(t.getProject()); // no project in tasks
        assertEquals(projectId, t.getProjectId()); // but yes project id

        assertNotNull(t.getStartedTime()); // should be started!
        assertNotNull(t.getStoppedTime()); // should be stopped!

        return t;
    }
}
