package com.example.demo.user.service;

import com.example.demo.user.domain.SpAuthority;
import com.example.demo.user.domain.SpOauth2User;
import com.example.demo.user.domain.SpUser;
import com.example.demo.user.repository.SpOauth2UserRepository;
import com.example.demo.user.repository.SpUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SpUserService implements UserDetailsService {

    @Autowired
    private SpUserRepository userRepository;

    @Autowired
    private SpOauth2UserRepository oauth2UserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow(
                ()->new UsernameNotFoundException(username));
    }

    //회원 조회
    public Optional<SpUser> findUser(String email) {
        return userRepository.findUserByEmail(email);
    }

    //커밋
    public SpUser save(SpUser user) {
        return userRepository.save(user);
    }

    //권한 추가
    public void addAuthority(Long userId, String authority){
        userRepository.findById(userId).ifPresent(user->{
            SpAuthority newRole = new SpAuthority(user.getUserId(), authority);
            if(user.getAuthorities() == null){
                HashSet<SpAuthority> authorities = new HashSet<>();
                authorities.add(newRole);
                user.setAuthorities(authorities);
                save(user);
            }else if(!user.getAuthorities().contains(newRole)){
                HashSet<SpAuthority> authorities = new HashSet<>();
                authorities.addAll(user.getAuthorities());
                authorities.add(newRole);
                user.setAuthorities(authorities);
                save(user);
            }
        });
    }

    //권한 제거
    public void removeAuthority(Long userId, String authority){
        userRepository.findById(userId).ifPresent(user->{
            if(user.getAuthorities()==null) return;
            SpAuthority targetRole = new SpAuthority(user.getUserId(), authority);
            if(user.getAuthorities().contains(targetRole)){
                user.setAuthorities(
                        user.getAuthorities().stream().filter(auth->!auth.equals(targetRole))
                                .collect(Collectors.toSet())
                );
                save(user);
            }
        });
    }

    //데이터 조회
    public SpUser loadUser(final SpOauth2User oauth2User){
        SpOauth2User user = oauth2UserRepository.findById(oauth2User.getOauth2UserId()).orElseGet(()->{
            SpUser spUser = new SpUser();
            spUser.setEmail(oauth2User.getEmail());
            spUser.setEnabled(true);
            spUser.setPassword("");
            userRepository.save(spUser);
            addAuthority(spUser.getUserId(), "ROLE_USER");
            oauth2User.setUserId(spUser.getUserId());
            oauth2User.setCreated(LocalDateTime.now());
            return oauth2UserRepository.save(oauth2User);
        });
        return userRepository.findById(user.getUserId()).get();
    }
}
