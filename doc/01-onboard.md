# Register

## *Frontend*

Field| Name
---|---
Input| Email
Button| Register

---

Method|POST
---|---
URL|/app/v1/register

---

## *Backend*

- Assign password with 6 character
- Create user with last login null
- Send password to provided email

---

## *Response*

### *Header*

``` text
"status":"200"
```
