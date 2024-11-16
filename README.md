# Simple Java Web

## Esse projeto é um pequeno framework para simplificar java na web. Baseado na usabilidade do spring mvc

### exemplo

```
@Controller
public class UserController {

    @RequestMapping(path = "/users", method = "GET")
    public String getUsers() {
        return "List of users!";
    }

    @RequestMapping(path = "/users", method = "POST")
    public String createUser() {
        return "User created!";
    }
}
```
