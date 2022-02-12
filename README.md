# JPASHOP (토이 프로젝트)

##### `SpringBoot` `JPA` `H2 Database` `intelliJ`

간단한 토이프로젝트로 주문 시스템을 제작하였습니다.
<hr>

### **ORM; Object-Relational Mapping; 객체 관계 매핑**

* 객체는 객체대로 설계하고, RDB는 RDB대로 설계했습니다.

* 관계형 데이터베이스에 테이블 회원, 주문, 아이템 등을 엔티티 클래스로 설계하였습니다.

### **JPA; Java Persistence API;**

* 영속성 관리와 ORM을 위한 표준 기술입니다.

### **View**

* Controller에서 데이터를 가공하여 View에 넘겼습니다.

* Post 요청으로 받아 새로운 Member를 생성하는 Controller입니다. Membere 엔티티 클래스에서 `@NotEmpty`가 있는 필드가 MemberForm에서 받아온 form에 없을 경우 BindingResult가 오류를 result에 넣게 됩니다.

* Get 요청으로 url `/members`을 클라이언트가 요청하면 회원 리스트를 model에 담아 View에 뿌려줍니다.

```java
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }


        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}

```

### **API**

* Controller에서 데이터를 가공하여 Jackson이 JSON 객체를 만들어 return 합니다.

* 엔티티를 직접 반환 시에 필요 없는 데이터까지 반환되므로 DTO를 만들어 주었습니다. 

```java
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> orderV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate; //주문시간
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}
```
![image](https://user-images.githubusercontent.com/82203978/153462576-4f28ad32-3822-4063-a433-989d8dd36b1d.png)
