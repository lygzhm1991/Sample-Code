# Sample-Code
The sample code is a mutual fund web application using MVC architecture, Java Servlet, Apache Tomcat and MySQL. 
The user interface was developed with HTML5, CSS and Javascript.
The web application solved database transaction problem and concurrency issues.

Through the web application, the employees could create account for customers, view profile of customers, deposit check to customers’ account, create account for other employees, change password for customers, create new fund, deal with customers’ transaction request. All the pending transaction request and deposit request are committed through clicking update in Transition Day.

The customers could change their own password, request deposit, buy fund and sell fund online, view their transaction history and see the price trend chart of funds.

Before initialisation, please drop original test database and create another database called “test” in MySQL. For the connivence of testing, the application will set up one customer account and two employee accounts once initialisation.

Employee Account1: User Name : admin
		               Password : admin
Employee Account2: User Name : Jason
                   Password: 1
Customer Account:  User Name : Jeff
                   Password : 1
