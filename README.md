# JobFAIR Backend Template

Spring Boot backend template sa generickim CRUD temeljom i jasnom podjelom slojeva. Trenutna implementacija koristi Example entitet kao referentni obrazac koji tim moze pratiti za sve naredne module.

## Tim

- Ajdin Šuta
- Almedina Pehlivan
- Emina Efendić
- Hana Mahmutović
- Selma Ljuhar

## Sta je trenutno implementirano

- Generic CRUD servis i controller osnova
- Example entitet sa kompletnim slojevima (DTO, mapper, repository, service, controller)
- Globalni exception handling i standardizovan API response
- OpenAPI/Swagger dokumentacija

## Kratko uputstvo za novi entitet

Prati Example kao sablon, korak po korak:

1. Domain model
- Dodaj entitet u src/main/java/com/jobfair/domain/model
- Naslijedi BaseEntity, definisi tabelu, sekvencu i polja

2. Repository
- Dodaj repository interfejs u src/main/java/com/jobfair/domain/repository
- Neka naslijedi GenericRepository<Entitet, Long>

3. DTO
- Request DTO dodaj u src/main/java/com/jobfair/api/dto/request
- Response DTO dodaj u src/main/java/com/jobfair/api/dto/response
- Validacije stavi na request DTO

4. Mapper
- Dodaj MapStruct mapper u src/main/java/com/jobfair/infrastructure/mapper
- Mapper treba naslijediti BaseMapper<Entitet, RequestDTO, ResponseDTO>

5. Service interfejs
- Dodaj interfejs u src/main/java/com/jobfair/domain/service
- Neka naslijedi BaseCrudService<Entitet, Long, RequestDTO, ResponseDTO>

6. Service implementacija
- Dodaj implementaciju u src/main/java/com/jobfair/application/serviceImpl
- Neka naslijedi AbstractCrudService<Entitet, Long, RequestDTO, ResponseDTO>
- Implementiraj resourceName i domenske validacije za create/update/patch

7. API putanja
- Dodaj konstantu u src/main/java/com/jobfair/shared/constants/ApiPaths

8. Controller
- Dodaj controller u src/main/java/com/jobfair/api/controller
- Neka naslijedi AbstractCrudController<Long, RequestDTO, ResponseDTO>
- Dodaj @RequestMapping sa putanjom iz ApiPaths i @Tag sa imenom entiteta

9. Baza
- Napravi ili izmijeni entitet u kodu
- Uskladi promjene na bazi prema internom timu/DBA procesu

10. Testovi
- Dodaj servis testove u src/test/java/com/jobfair/application/serviceImpl
- Dodaj controller testove u src/test/java/com/jobfair/api/controller
- Pokrij sve CRUD rute i negativne slucajeve