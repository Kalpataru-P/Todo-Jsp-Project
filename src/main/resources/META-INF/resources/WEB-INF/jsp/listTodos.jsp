<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
  <head>
    <title>Todo List Page</title>
  </head>
  <body>
    <div>
      <h1>Welcome to Todo List Page ${username}</h1>
      <table class="table">
        <thead>
          <tr>
            <th>Id</th>
            <th>User Name</th>
            <th>Description</th>
            <th>Target Date</th>
            <th>Is Done?</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${todoList}" var="todo">
            <tr>
              <td>${todo.id}</td>
              <td>${todo.username}</td>
              <td>${todo.description}</td>
              <td>${todo.targetDate}</td>
              <td>${todo.done}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </body>
</html>
