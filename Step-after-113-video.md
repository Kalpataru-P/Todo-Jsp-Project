### step1 : add required field(required="required")

### todo.jsp

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
  <head>
    <title>Add Todo Page</title>
    <link rel="stylesheet" href="webjars\bootstrap\5.1.3\css\bootstrap.css" />
  </head>
  <body>
    <div class="container">
      <h1>Enter todo details</h1>
      <form method="post">
        Description:
        <input type="text" name="description" required="required" /> 
        <!-- Check above -->
        Summit:
        <input type="submit" class="btn-success" />
      </form>
    </div>
    <script src="webjars\jquery\3.6.0\jquery.js"></script>
    <script src="webjars\bootstrap\5.1.3\js\bootstrap.js"></script>
  </body>
</html>

### TodoController.java

@Controller
@SessionAttributes("username")
public class TodoController {
private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // @RequestMapping("list-todos")
    // public String listAllTodos(ModelMap model){
    // List<Todo> todoList = todoService.findByUserName("Kalpataru");
    // model.put("todoList",todoList);
    // return "listTodos";
    // }

    @RequestMapping("list-todos")
    public String listAllTodos(ModelMap model) {
        List<Todo> todoList = todoService.findByUserName("Kalpataru");
        model.put("todoList", todoList);
        return "listTodosWithCss";
    }

    @RequestMapping(value = "add-todo", method = RequestMethod.GET)
    public String showNewTodoPage() {

        return "todo";
    }

    @RequestMapping(value = "add-todo", method = RequestMethod.POST)
    public String addNewTodoPage(ModelMap map, @RequestParam String description) {
        String username = (String) map.get("username");
        todoService.addTodo(username, description,
                LocalDate.now().plusYears(1), false);
        return "redirect:list-todos";
    }

### STEP2

 <!-- Validation Dependency -->

         <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>

### Add Spring Form tag todo.jsp

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

### Modified todo.jsp

## OLD

<form method="post">
        Description:
        <input type="text" name="description" required="required" /> 
        <!-- Check above -->
        Summit:
        <input type="submit" class="btn-success" />
      </form>

## NEW

<form:form method="post" modelAttribute="todo">
Description:
<form:input type="text" path="description" required="required" />
<form:input type="hidden" path="id" />
<form:input type="hidden" path="done" />
<input type="submit" class="btn-success" />
</form:form>

<!-- OR -->

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!-- form tag -->

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
  <head>
    <title>Add Todo Page</title>
    <link rel="stylesheet" href="webjars\bootstrap\5.1.3\css\bootstrap.css" />
  </head>
  <body>
    <div class="container">
      <h1>Enter todo details</h1>
      <form:form method="post" modelAttribute="todo">
        Description:
        <form:input type="text" path="description" required="required" />
        <form:input type="hidden" path="id" />
        <form:input type="hidden" path="done" />
        <input type="submit" class="btn-success" />
      </form:form>
    </div>
    <script src="webjars\jquery\3.6.0\jquery.js"></script>
    <script src="webjars\bootstrap\5.1.3\js\bootstrap.js"></script>
  </body>
</html>

### Modified TodoController.java

## OLD

@RequestMapping(value = "add-todo", method = RequestMethod.GET)
public String showNewTodoPage() {

        return "todo";
    }

    @RequestMapping(value = "add-todo", method = RequestMethod.POST)
    public String addNewTodoPage(ModelMap map, @RequestParam String description) {
        String username = (String) map.get("username");
        todoService.addTodo(username, description,
                LocalDate.now().plusYears(1), false);
        return "redirect:list-todos";
    }

## New

@RequestMapping(value = "add-todo", method = RequestMethod.GET)
public String showNewTodoPage(ModelMap model) {
String username = (String) model.get("username");
Todo todo = new Todo(0, username, "Default Desc", LocalDate.now().plusYears(1), false);
model.put("todo", todo);
return "todo";
}

    @RequestMapping(value = "add-todo", method = RequestMethod.POST)
    public String addNewTodoPage(ModelMap model, Todo todo) {
        String username = (String) model.get("username");
        todoService.addTodo(username, todo.getDescription(),
                LocalDate.now().plusYears(1), false);
        return "redirect:list-todos";
    }

### Step3 : Use of Validation Dependency

### Todo.java Modified

import jakarta.validation.constraints.Size;
@Size(min=10, message="Enter atleast 10 characters")
private String description;

### TodoController.java Modified

import jakarta.validation.Valid;

### Old

@RequestMapping(value = "add-todo", method = RequestMethod.POST)
public String addNewTodoPage(ModelMap model, Todo todo) {
String username = (String) model.get("username");
todoService.addTodo(username, todo.getDescription(),
LocalDate.now().plusYears(1), false);
return "redirect:list-todos";
}

## New

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@RequestMapping(value="add-todo", method = RequestMethod.POST)
public String addNewTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
if(result.hasErrors()) {
return "todo";
}
String username = (String)model.get("name");
todoService.addTodo(username, todo.getDescription(),
LocalDate.now().plusYears(1), false);
return "redirect:list-todos";
}

### Modified todo.jsp

## OLD

<form:form method="post" modelAttribute="todo">
Description:
<form:input type="text" path="description" required="required" />
<form:input type="hidden" path="id" />
<form:input type="hidden" path="done" />
<input type="submit" class="btn-success" />
</form:form>

## New

Description:
<form:input type="text" path="description" required="required" />
<form:errors path="description" cssClass="text-warning"/>
<form:input type="hidden" path="id" />
<form:input type="hidden" path="done" />
<br>
<input type="submit" class="btn-success" />

## STEP4 Add delete every list

## my-first-web-app\src\main\resources\META-INF\resources\WEB-INF\jsp\listTodosWithCss.jsp

<!-- Add bellow 2 lines -->
 <th>Actions</th>
  <td><a href="delete-todo" ?id="${todo.id}" class="btn-warning">DELETE</a></td>
  
### listTodosWithCss.jsp  Modified

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
  <head>
    <title>Welcome to Todo List Page</title>
   <link rel="stylesheet" href="webjars\bootstrap\5.1.3\css\bootstrap.css" />
  </head>
  <body>
    <div class="container">
      <h1>Your Todo's</h1>
      <table class="table">
        <thead>
          <tr>
            <th>Id</th>
            <!-- <th>User Name</th> -->
            <th>Description</th>
            <th>Target Date</th>
            <th>Is Done?</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${todoList}" var="todo">
            <tr>
              <td>${todo.id}</td>
              <!-- <td>${todo.username}</td> -->
              <td>${todo.description}</td>
              <td>${todo.targetDate}</td>
              <td>${todo.done}</td>
              <td><a href="delete-todo" ?id="${todo.id}" class="btn-warning">DELETE</a></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
      <a href="add-todo" , class="btn-success"> Add Todo</a>
    </div>
    <!-- <script src="webjars\jquery\3.6.0\jquery.min.js">    </script>
    <script src="webjars\bootstrap\5.1.3\js\bootstrap.min.js"> -->
    <script src="webjars\jquery\3.6.0\jquery.js"></script>
    <script src="webjars\bootstrap\5.1.3\js\bootstrap.js"></script>
  </body>
</html>

## STEP5 Add update every list

## my-first-web-app\src\main\resources\META-INF\resources\WEB-INF\jsp\listTodosWithCss.jsp

<!-- Add bellow  line -->

  <td><a href="update-todo" ?id="${todo.id}" class="btn-warning">UPDATE</a></td>
  
### listTodosWithCss.jsp  Modified

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
  <head>
    <title>Welcome to Todo List Page</title>
   <link rel="stylesheet" href="webjars\bootstrap\5.1.3\css\bootstrap.css" />
  </head>
  <body>
    <div class="container">
      <h1>Your Todo's</h1>
      <table class="table">
        <thead>
          <tr>
            <th>Id</th>
            <!-- <th>User Name</th> -->
            <th>Description</th>
            <th>Target Date</th>
            <th>Is Done?</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${todoList}" var="todo">
            <tr>
              <td>${todo.id}</td>
              <!-- <td>${todo.username}</td> -->
              <td>${todo.description}</td>
              <td>${todo.targetDate}</td>
              <td>${todo.done}</td>
             <td><a href="update-todo" ?id="${todo.id}" class="btn-warning">UPDATE</a></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
      <a href="add-todo" , class="btn-success"> Add Todo</a>
    </div>
     <script src="webjars\jquery\3.6.0\jquery.js"></script>
    <script src="webjars\bootstrap\5.1.3\js\bootstrap.js"></script>
  </body>

### my-first-web-app\src\main\java\com\learn\springboot\myfirstwebapp\todo\TodoService.java Modified

<!-- Add two methods -->

public Todo findById(int id) {
Predicate<? super Todo> predicate = todo -> todo.getId() == id;
Todo todo = todos.stream().filter(predicate).findFirst().get();
return todo;
}

public void updateById(@Valid Todo todo) {

deleteById(todo.getId());
todos.add(todo);

}

### my-first-web-app\src\main\java\com\learn\springboot\myfirstwebapp\todo\TodoController.java Modified

<!-- Add two methods -->

@RequestMapping(value = "update-todo", method = RequestMethod.GET)
public String showUpdatePage(@RequestParam int id, ModelMap model) {
Todo todo = todoService.findById(id);
model.addAttribute("todo", todo);
return "todo";

    }

@RequestMapping(value = "update-todo", method = RequestMethod.POST)
public String updateSingleTodo(ModelMap model, @Valid Todo todo, BindingResult result) {

        if (result.hasErrors()) {
            return "todo";
        }

        String username = (String) model.get("username");
        todo.setUsername(username);
        todoService.updateById(todo);
        return "redirect:list-todos";
    }

### STEP6 : Add date picker Dependency

<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>bootstrap-datepicker</artifactId>
    <version>1.7.1</version>
 </dependency>

### /src/main/resources/META-INF/resources/WEB-INF/jsp/todo.jsp Modified

```
<link href="webjars/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.standalone.min.css" rel="stylesheet" >

<fieldset class="mb-3">
	<form:label path="description">Description</form:label>
	<form:input type="text" path="description" required="required"/>
	<form:errors path="description" cssClass="text-warning"/>
</fieldset>
<fieldset class="mb-3">
	<form:label path="targetDate">Target Date</form:label>
	<form:input type="text" path="targetDate" required="required"/>
	<form:errors path="targetDate" cssClass="text-warning"/>
</fieldset>

<script src="webjars/jquery/3.6.0/jquery.min.js"></script>
<script src="webjars/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript">
	$('#targetDate').datepicker({
	    format: 'yyyy-mm-dd'
	});
</script>

```

### /src/main/resources/application.properties Modified

```
spring.mvc.format.date=yyyy-MM-dd
```

### /src/main/resources/META-INF/resources/WEB-INF/jsp/common/footer.jspf

```
<script src="webjars/bootstrap/5.1.3/js/bootstrap.min.js"></script>
		<script src="webjars/jquery/3.6.0/jquery.min.js"></script>
		<script src="webjars/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>

	</body>
</html>
```

---

### /src/main/resources/META-INF/resources/WEB-INF/jsp/common/header.jspf

```
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html>
	<head>
		<link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet" >
		<link href="webjars/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.standalone.min.css" rel="stylesheet" >

		<title>Manage Your Todos</title>
	</head>
	<body>
```

---

### /src/main/resources/META-INF/resources/WEB-INF/jsp/common/navigation.jspf

```
<nav class="navbar navbar-expand-md navbar-light bg-light mb-3 p-1">
	<a class="navbar-brand m-1" href="https://courses.in28minutes.com">in28minutes</a>
	<div class="collapse navbar-collapse">
		<ul class="navbar-nav">
			<li class="nav-item"><a class="nav-link" href="/">Home</a></li>
			<li class="nav-item"><a class="nav-link" href="/list-todos">Todos</a></li>
		</ul>
	</div>
	<ul class="navbar-nav">
		<li class="nav-item"><a class="nav-link" href="/logout">Logout</a></li>
	</ul>
</nav>
```

---

### /src/main/resources/META-INF/resources/WEB-INF/jsp/listTodos.jsp Modified

```
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<div class="container">
	<h1>Your Todos</h1>
	<table class="table">
		<thead>
			<tr>
				<th>Description</th>
				<th>Target Date</th>
				<th>Is Done?</th>
				<th></th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${todos}" var="todo">
				<tr>
					<td>${todo.description}</td>
					<td>${todo.targetDate}</td>
					<td>${todo.done}</td>
					<td> <a href="delete-todo?id=${todo.id}" class="btn btn-warning">Delete</a>   </td>
					<td> <a href="update-todo?id=${todo.id}" class="btn btn-success">Update</a>   </td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a href="add-todo" class="btn btn-success">Add Todo</a>
</div>
<%@ include file="common/footer.jspf" %>
```

### /src/main/resources/META-INF/resources/WEB-INF/jsp/todo.jsp Modified

```
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<div class="container">

	<h1>Enter Todo Details</h1>

	<form:form method="post" modelAttribute="todo">
		<fieldset class="mb-3">
			<form:label path="description">Description</form:label>
			<form:input type="text" path="description" required="required"/>
			<form:errors path="description" cssClass="text-warning"/>
		</fieldset>
		<fieldset class="mb-3">
			<form:label path="targetDate">Target Date</form:label>
			<form:input type="text" path="targetDate" required="required"/>
			<form:errors path="targetDate" cssClass="text-warning"/>
		</fieldset>
		<form:input type="hidden" path="id"/>
		<form:input type="hidden" path="done"/>
		<input type="submit" class="btn btn-success"/>

	</form:form>

</div>
<%@ include file="common/footer.jspf" %>
<script type="text/javascript">
	$('#targetDate').datepicker({
	    format: 'yyyy-mm-dd'
	});
</script>
```
