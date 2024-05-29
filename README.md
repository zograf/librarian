# Librarian

- Librarian is a software made for recommending books. It is a "reader's advisory" tool made to help people choose their next book

### How to run

- Install the `model` application (`kjar` and `service` do not need to be installed)
- In the `application.properties` of `service` application set the `hibernate.ddl-auto` field to `create`
- Uncomment the `LoadData()` function inside the `@PostConstruct` of the `ServiceApplication.java`
- Set the path inside the `LoadData()` function to the `.csv` files
- Run `docker compose up` inside of librarian-db folder to startup the PostgreSQL database
- Run the service application and wait approx. 5 minutes for the data to load.
- Shut down the application and change the `hibernate.ddl-auto` field from `create` back to `update`
- Comment the `LoadData()` function inside the `@PostConstruct` of the `ServiceApplication.java`
- Run the application again. It should start in approx 80 seconds.
- Use `npm start` inside of librarian/front to start up the frontend application (it will serve on `localhost:5000`)
- Create an account, populate the preferences tab with subjects and authors and enjoy!