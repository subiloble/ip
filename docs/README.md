# Eureka User Guide

## Introduction

Eureka is your intelligent assistant designed to help you manage tasks efficiently. 

With a wise and insightful personality, Eureka can track tasks, set deadlines, schedule events, and provide helpful reminders in a structured yet engaging way.

## Features  

- 📝 **Task Management** - Add, mark, unmark, list, and delete tasks.  
- ⏳ **Deadline Tracking** - Set deadlines for tasks.  
- 📅 **Event Scheduling** - Schedule events with start and end times.  
- 🔎 **Find Tasks** - Search for tasks by description.  
- 📆 **Check Dates** - Find tasks happening on a specific date.  
- ⏪ **Undo History** - Revert to a previous task state.  
- 🚨 **Error Handling** - Get warnings for incorrect commands. 

### **1. Adding a ToDo Task**

**Command:**  
```plaintext
todo <task description>
```

### **2. Adding a Deadline Task**

**Command:**  
```plaintext
deadline <task description> /by <YYYY-MM-DD HHmm>
```
* Both description and datetime are necessary

### **3. Adding a Deadline Task**

**Command:**  
```plaintext
event <event description> /from <YYYY-MM-DD HHmm> /to <YYYY-MM-DD HHmm>
```
* Description, from datetime and to datetime are all required.

### **4. Listing All Tasks**  

**Command:**  
```plaintext
list
```

### **5. Finding Tasks**  

**Command:**  
```plaintext
find <keyword>
```
* Finding the tasks are contain the keyword in their description.

### **6. Checking Tasks on a Specific Date** 

**Command:**  
```plaintext
check <YYYY-MM-DD>
```
* For todo tasks, this is not applicable. 
* For event tasks, as long as the date input is in the duration, the event counts as one task found.

### **7. Marking a Task as Done**  

**Command:**  
```plaintext
mark <task number>
```

### **8. Unmarking a Task**  

**Command:**  
```plaintext
unmark <task number>
```

### **9. Deleting a Task**  

**Command:**  
```plaintext
delete <task number>
```
* Deleting tasks by indices.

### **10. Undoing the Last Change** 

**Command:**  
```plaintext
undo
```
* Can repeatedly undo until no last operation can be found, meaning to the original state as users started Eureka.

### **11. Exiting Eureka** 

**Command:**  
```plaintext
bye
```