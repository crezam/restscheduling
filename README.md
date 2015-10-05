# rest-scheduling

A RESTful API for appointments

## Build instructions (Maven):

`mvn clean install`

## Exposes following RESTful API

POST
/appointments/appointment
Adds a new Appointment to the database. Only will accept dates in the future and will not update if there is an overlap with an existing appointment

GET
/appointments
Lists all Appointments

GET
/appointments/{id}
Retrieves the appointment with specified id. Returns a 404 Not found code if it doesn’t exist

PUT
/appointments/{id}
Updates the Appointment if it already exists, otherwise creates a new one with the id specified. It won’t update or create if the new dates are overlapping with another appointment

DELETE
/appointments/{id}
Deletes the Appointment that has specified Id if it exists. Otherwise has no effect

POST
/search/appointments
Provides search functionality. Takes two parameters: a start timestamp and end timestamp and will list all Appointments within that range (including partially).
Note the start and end timestamp values are passed in

