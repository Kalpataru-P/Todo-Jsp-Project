<%@ include file="common/header.jspf" %> <%@ include
file="common/navigator.jspf" %>

<div class="container">
  <h1>Your Todo's</h1>
  <table class="table">
    <thead>
      <tr>
        <th>Description</th>
        <th>Target Date</th>
        <th>Is Done?</th>
        <th>Actions</th>
      </tr>
    </thead>
    <tbody>
    
      <c:forEach items="${todoList}" var="todo">
        <tr>
          <td>${todo.description}</td>
          <td>${todo.targetDate}</td>
          <td>${todo.done}</td>
          <td>
            <a href="delete-todo?id=${todo.id}" class="btn btn-warning"
              >DELETE</a
            >
          </td>
          <td>
            <a href="update-todo?id=${todo.id}" class="btn btn-success"
              >UPDATE</a
            >
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  <a href="add-todo"  class="btn btn-success"> Add Todo</a>
</div>
<%@ include file="common/footer.jspf" %>
