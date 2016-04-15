package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * Created by Fatma AÇAR on 13.04.2016.
 */

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Member> createBook(@RequestBody Map<String, Object> MemberMap) {
        try {
            Member member = new Member(MemberMap.get("firstName").toString(),
                    MemberMap.get("lastName").toString(),
                    Integer.parseInt(MemberMap.get("number").toString()));
            Member member1 = memberRepository.save(member);

            return new ResponseEntity<Member>(member1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Member>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{memberId}")
    public ResponseEntity<Member> getUser(@PathVariable("memberId") String memberId) {
        try {

            Member member = memberRepository.findOne(memberId);
            if (member == null) {
                return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Member>(member, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Member>(HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/{memberId}")
    public ResponseEntity<Member> editBook(@PathVariable("memberId") String memberId,
                                           @RequestBody Map<String, Object> MemberMap) {
        try {
            Member isMember = memberRepository.findOne(memberId);
            if (isMember == null) {
                return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
            }
            Member member = new Member(MemberMap.get("firstName").toString(),
                    MemberMap.get("lastName").toString(),
                    Integer.parseInt(MemberMap.get("number").toString()));

            member.setId(memberId);

            Member member1 = memberRepository.save(member);
            return new ResponseEntity<Member>(member1, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Member>(HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{memberId}")
    public ResponseEntity<Member> deleteBook(@PathVariable("memberId") String memberId) {

        try {
            Member member = memberRepository.findOne(memberId);
            if (member == null) {
                return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
            } else {
                memberRepository.delete(memberId);
                return new ResponseEntity<Member>(HttpStatus.OK);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<Member>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<Member>(HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Member>> getAllBooks() {

        List<Member> members = memberRepository.findAll();
        if (members == null) {
            return new ResponseEntity<List<Member>>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<Member>>(members, HttpStatus.OK);
    }

}



