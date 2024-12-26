package ru.mtuci.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import java.util.List;

// реализация интерфейса
@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    // логин
    private final String username;

    // пароль
    private final String password;

    // права доступа
    private List<GrantedAuthority> authorities;

    // состояние активности аккаунта
    private final boolean isActive;

    // Метод, который проверяет, не истек ли срок действия аккаунта
    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    // не истек ли срок действия учетных данных
    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    // активирован ли аккаунт
    @Override
    public boolean isEnabled() {
        return isActive;
    }

    // не заблокирован ли аккаунт
    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    // метод для создания объекта UserDetailsImpl
    public static UserDetails fromUser(ru.mtuci.demo.models.User user) {
        return new User(
                user.getLogin(),
                user.getPasswordHash(),
                user.getRole().getGrantedAuthorities()
        );
    }
}
