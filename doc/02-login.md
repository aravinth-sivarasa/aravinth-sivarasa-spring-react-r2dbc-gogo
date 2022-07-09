# Login

## *Frontend*

Field| Name
---|---
Input| Email
Password| Password
Button| Submit (api)

---

## API

Method|POST
---|---
URL|/app/v1/login

---

## *Response*

### *Header*

``` text
"status":"200"
```

### *Body*

```json
{
    "lastLoginAt":""
}
```

## Actions

```java
if (lastLoginAt == null){
    redirect to change password
} else {
    redirect to dashboard
}
```
