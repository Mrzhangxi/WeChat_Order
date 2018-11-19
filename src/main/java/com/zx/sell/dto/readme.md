什么是DTO？
百度百科如何解读的？
 
DTO是Data Transfer Object 的简写，既数据传输对象。
是一种设计模式之间传输数据的软件应用系统。数据传输目标往往是数据访问对象从数据库中检索的数据。数据传输对象与数据交互对象或数据访问对象之间是一个不具备有任何行为除了存储和检索的数据。（访问和存取器）
 
维基百科是如何解读的？
 
A data transfer object(DTO) is an  object that carries data between two process.
The difference between data transfer and business objects or data access objects is that a DTO does not have any behavior except for storage and retrieval of its own data(mutators and accesssors).DTOs are simple objects that should not contain any business logic that would require testing.
 
和百度百科不同的是，DTO和MO（Model Object）与BO（Business Object）的不同之处在于DTO没有任何业务行为（贫血模式）只作为数据的存储。