import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class SingleTask {
    String name;
    int priority;
    int status;

    SingleTask(int priority, String name, int status) {
        this.priority = priority;
        this.name = name;
        this.status = status;
    }
}

class TaskComparator implements Comparator<SingleTask> {
    public int compare(SingleTask t1, SingleTask t2) {
        if (t1.priority == t2.priority)
            return 0;
        else if (t1.priority > t2.priority)
            return 1;
        else
            return -1;
    }
}

class Tasks {
    ArrayList<SingleTask> pending = new ArrayList<SingleTask>();
    ArrayList<SingleTask> completed = new ArrayList<SingleTask>();
    String data_file;

    Tasks (String data_file) {
        this.data_file = data_file;
    }


    void load() 
    {
        File data = new File(data_file);
        if (!data.exists()) {
            // creating the file
            try {
                data.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                FileWriter writer = new FileWriter(data);
                writer.write("Name,Priority,Status\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // reading from file into memory
        try {
            Scanner sc = new Scanner(data);
            sc.nextLine();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                
                String []fields = line.split(",");
                SingleTask new_task= new SingleTask(Integer.parseInt(fields[1]), fields[0], Integer.parseInt(fields[2]));
                if (new_task.status == 0) {
                    pending.add(new_task);
                }
                else {
                    completed.add(new_task);
                }
            }
            sc.close();
            
        } catch (Exception e) {};

        // sorting the pending arraylist
        Collections.sort(pending, new TaskComparator());
    }

    void ls() 
    {
        if (pending.size() == 0) {
            System.out.print("There are no pending tasks!\n");
        }

        else 
        {   int idx=1;
            for(SingleTask a : pending)
            {   
                System.out.printf("%d. %s [%d]\n",idx,a.name,a.priority);
                idx++;
            }
        }
    }

    void report()
    {
        int pend= pending.size();
        int comp = completed.size();

        System.out.print("Pending : "+pend+"\n");
        int idx=1;
        for(SingleTask a : pending) {   
            System.out.printf("%d. %s [%d]\n",idx,a.name,a.priority);
            idx++;
        }
        System.out.print("\nCompleted : "+comp+"\n");
        idx=1;
        for(SingleTask a : completed) {   
            System.out.printf("%d. %s\n",idx,a.name);
            idx++;
        }
    }

    void add(SingleTask newtask)
    {
        for (int i = 0, pend_size = pending.size(); i < pend_size; i++) {
            if (pending.get(i).name.equals(newtask.name)) {
                pending.remove(pending.get(i));
                break;
            }
        }
        for (int i = 0, comp_size = completed.size(); i < comp_size; i++) {
            if (completed.get(i).name.equals(newtask.name)) {
                completed.remove(completed.get(i));
                break;
            }
        }
        pending.add(newtask);

        
        File data = new File(data_file);
        
        // creating the file if it doesn't exist
        if (!data.exists()) {
            try {
                data.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                FileWriter writer = new FileWriter(data_file);
                writer.write("Name,Priority,Status\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        // write to file
        try {
            // writing to file from the lists
            FileWriter writer = new FileWriter(data);
            writer.write("Name,Priority,Status\n");
            for (SingleTask t : pending) {
                writer.write(t.name + "," + t.priority + "," + t.status + "\n");
            }
            for (SingleTask t : completed) {
                writer.write(t.name + "," + t.priority + "," + t.status + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.printf("Added task: \"%s\" with priority %d\n",newtask.name,newtask.priority);
    }
    
    void delete(int index)
    {
        index=index-1;
        if(index < pending.size() && index >=0) {
            pending.remove(index);
            System.out.println("Deleted task #"+(index+1));

            // write to file
            try {
                File data = new File(data_file);
                // writing to file from the lists
                FileWriter writer = new FileWriter(data);
                writer.write("Name,Priority,Status\n");
                for (SingleTask t : pending) {
                    writer.write(t.name + "," + t.priority + "," + t.status + "\n");
                }
                for (SingleTask t : completed) {
                    writer.write(t.name + "," + t.priority + "," + t.status + "\n");
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else 
            System.out.println("Error: task with index #"+(index+1)+" does not exist. Nothing deleted.");
    }

    void done(int index)
    {
        index=index-1;
        if(index <pending.size() && index >=0) {
            
            pending.get(index).status = 1;
            completed.add(pending.get(index));
            pending.remove(index);

            // write to file
            try {
                File data = new File(data_file);
                // writing to file from the lists
                FileWriter writer = new FileWriter(data);
                writer.write("Name,Priority,Status\n");
                for (SingleTask t : pending) {
                    writer.write(t.name + "," + t.priority + "," + t.status + "\n");
                }
                for (SingleTask t : completed) {
                    writer.write(t.name + "," + t.priority + "," + t.status + "\n");
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.print("Marked item as done.\n");
        }

        else 
            System.out.println("Error: no incomplete item with index #" + (index + 1) +" exists.");
    }
}

public class Task
{
    public static void main(String args[]) {

        String h1 = "Usage :-\n";
        String h2 = "$ ./task add 2 hello world    # Add a new item with priority 2 and text \"hello world\" to the list\n";
        String h3 = "$ ./task ls                   # Show incomplete priority list items sorted by priority in ascending order\n";
        String h4 = "$ ./task del INDEX            # Delete the incomplete item with the given index\n";
        String h5 = "$ ./task done INDEX           # Mark the incomplete item with the given index as complete\n";
        String h6 = "$ ./task help                 # Show usage\n";
        String h7 = "$ ./task report               # Statistics";

        String help = h1 + h2 + h3 + h4 + h5 + h6 + h7;
        if (args.length == 0) {
            System.out.print(help);
            System.exit(0);
        }
        Tasks tk = new Tasks("test.csv");
        tk.load();
        if(args[0].equals("add"))
        {
            if(args.length ==3)
            {
                SingleTask tm = new SingleTask(Integer.parseInt(args[1]),args[2],0);
                tk.add(tm);
            }
            else 
            {
                System.out.println("Error: Missing tasks string. Nothing added!");
            }
        }
        

        else if(args[0].equals("ls"))
        {
            tk.ls();
        }

        else if(args[0].equals("del"))
        {
            if(args.length==2)
            {
                tk.delete(Integer.parseInt(args[1]));
            } 
            else  
            {
                System.out.println("Error: Missing NUMBER for deleting tasks.");
            }
        }

        else if(args[0].equals("done"))
        {
            if(args.length==2)
            {
                tk.done(Integer.parseInt(args[1]));
            }
            else 
            {
                System.out.println("Error: Missing NUMBER for marking tasks as done.");
            }
        }


        else if(args[0].equals("help"))
        {  
            System.out.print(help);
        }

        else if(args[0].equals("report"))
        {
            tk.report();
        }


    }
}
