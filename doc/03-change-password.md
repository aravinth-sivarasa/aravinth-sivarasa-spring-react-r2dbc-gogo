
# Change password

## *Frontend*

Field| Name
---|---
Password| New password
Password| Confirmed Password
Button| Submit (api)

---

## API

Method|POST
---|---
URL|/app/v1/password
header| `Authorization: Basic YWRtaW46YWRtaW4=`
Body

``` json
{
    "newPassword":"pwd"
}
```

---

## *Backend*

- Update password
- Update lastLoginAt to current time

---

## *Response*

### *Header*

``` json
{
    "status": 200
}
```

## Actions

```java
redirect to dashboard
```
