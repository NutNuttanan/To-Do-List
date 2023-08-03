import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Task
{
	private String name;
	private boolean completed;

	public Task(String name)
	{
		this.name = name;
		this.completed = false;
	}

	public String getName()
	{
		return name;
	}

	public boolean isCompleted()
	{
		return completed;
	}

	public void setCompleted(boolean completed)
	{
		this.completed = completed;
	}

	public String toFileString()
	{
		return name + "|" + completed;
	}

	public static Task fromFileString(String fileString)
	{
		String[] parts = fileString.split("\\|");
		String name = parts[0];
		boolean completed = Boolean.parseBoolean(parts[1]);
		Task task = new Task(name);
		task.setCompleted(completed);
		return task;
	}
}

public class ToDoList
{
	private ArrayList<Task> tasks;

	public ToDoList()
	{
		this.tasks = new ArrayList<>();
	}

	public void addTask(Task task)
	{
		tasks.add(task);
	}

	public void showTasks()
	{
		System.out.println("To-Do List:");
		for (int i = 0; i < tasks.size(); i++)
		{
			Task task = tasks.get(i);
			System.out.println((i + 1) + ". [" + (task.isCompleted() ? "X" : " ") + "] " + task.getName());
		}
	}

	public void markTaskAsCompleted(int index)
	{
		if (index >= 0 && index < tasks.size())
		{
			Task task = tasks.get(index);
			task.setCompleted(true);
			System.out.println("Task '" + task.getName() + "' marked as completed.");
		} else
		{
			System.out.println("Invalid task index.");
		}
	}

	public void removeTask(int index)
	{
		if (index >= 0 && index < tasks.size())
		{
			Task task = tasks.remove(index);
			System.out.println("Task '" + task.getName() + "' removed.");
		} else
		{
			System.out.println("Invalid task index.");
		}
	}

	public void saveToFile(String filename)
	{
		try (PrintWriter writer = new PrintWriter(new FileWriter(filename)))
		{
			for (Task task : tasks)
			{
				writer.println(task.toFileString());
			}
			System.out.println("To-Do List saved to " + filename);
		} catch (IOException e)
		{
			System.out.println("Error saving To-Do List to file.");
		}
	}

	public void loadFromFile(String filename)
	{
		try (Scanner scanner = new Scanner(new File(filename)))
		{
			tasks.clear();
			while (scanner.hasNextLine())
			{
				String fileString = scanner.nextLine();
				Task task = Task.fromFileString(fileString);
				tasks.add(task);
			}
			System.out.println("To-Do List loaded from " + filename);
		} catch (FileNotFoundException e)
		{
			System.out.println("File not found: " + filename);
		}
	}

	public static void main(String[] args)
	{
		ToDoList toDoList = new ToDoList();
        Scanner scanner = new Scanner(System.in);

		while (true)
		{
			System.out.println("1. Add Task");
			System.out.println("2. Show Tasks");
			System.out.println("3. Mark Task as Completed");
			System.out.println("4. Remove Task");
			System.out.println("5. Save To-Do List");
            System.out.println("6. Load To-Do List");
			System.out.println("7. Exit");
			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume the new line character

			switch (choice)
			{
			case 1:
				System.out.print("Enter task name: ");
				String taskName = scanner.nextLine();
				Task task = new Task(taskName);
				toDoList.addTask(task);
				System.out.println("Task added.");
				break;
			case 2:
				toDoList.showTasks();
				break;
			case 3:
				System.out.print("Enter the task index to mark as completed: ");
				int taskIndex = scanner.nextInt();
				scanner.nextLine(); // Consume the new line character
				toDoList.markTaskAsCompleted(taskIndex - 1);
				break;
			case 4:
				System.out.print("Enter the task index to remove: ");
				int removeIndex = scanner.nextInt();
				scanner.nextLine(); // Consume the new line character
				toDoList.removeTask(removeIndex - 1);
				break;
			case 5:
				System.out.print("Enter filename to save: ");
				String saveFilename = scanner.nextLine();
				toDoList.saveToFile(saveFilename);
				break;
			case 6:
				System.out.print("Enter filename to load: ");
				String loadFilename = scanner.nextLine();
				toDoList.loadFromFile(loadFilename);
				break;
			case 7:
				System.out.println("Exiting...");
				scanner.close();
				System.exit(0);
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
				break;
			}
		}
	}
}
