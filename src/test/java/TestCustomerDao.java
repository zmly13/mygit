import com.itcast.dao.CustomerDao;
import com.itcast.pojo.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestCustomerDao {
    @Autowired
    private CustomerDao customerDao;
    @Test
    public void text1() {
        Customer customer = new Customer();
        customer.setCustName("三牛");
        customerDao.save(customer);
    }
    @Test
    public void text2() {
        Customer customer = customerDao.findById(3L).orElse(null);
        System.out.println(customer);

    }

    @Test
    //@Transactional
   // @Rollback(false)
    public void text3() {
        Customer customer = customerDao.findById(3L).orElse(null);
        //customer.setCustLevel("mvp");
        //customer.setCustPhone("18656893562");
        customerDao.save(customer);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void text4() {
        //延迟加载
        Customer customer = customerDao.getOne(3L);
        System.out.println(customer);
    }

    @Test
    public void text5() {
        List<Customer> customers = customerDao.findAll();
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    @Test
    public void text6() {
        Sort sort = new Sort(Sort.Direction.DESC,"custName");
        List<Customer> customers = customerDao.findAll(sort);
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }
    @Test
    public void text7() {
        //Sort sort = new Sort(Sort.Direction.DESC,"custName");
        Sort.Order order1 = new Sort.Order(Sort.Direction.ASC, "custName");
        //Sort.Order order2 = new Sort.Order(Sort.Direction.DESC, "custId");
        Sort sort = Sort.by(order1);
        List<Customer> customers = customerDao.findAll(sort);
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    @Test
    public void text8() {
        long total = customerDao.count();
        System.out.println(total);
    }

    @Test
    public void text9() {
        boolean flag = customerDao.existsById(1L);
        System.out.println(flag);
    }

    @Test
    public void text10() {
        List<Customer> customerAll = customerDao.findCustomerAll();
        for (Customer customer : customerAll) {
            System.out.println(customer);
        }
    }

    @Test
    public void text11() {
        List<Customer> customerList = customerDao.findCustomerByCustName("神仙姐姐");
        for (Customer customer : customerList) {
            System.out.println(customer);
        }
    }

    @Test
    @Transactional
    @Rollback(false)
    public void text12() {
        customerDao.updateBycustId("刘亦菲", 3L);
    }


    @Test
    public void text13() {
        Customer custName = customerDao.findByCustName("刘亦菲");
        System.out.println(custName);


    }


    @Test
    public void test11() {
        List<Customer> list = customerDao.findByCustNameNotLikeOrCustIndustryIsNotNull("%头%");
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    @Test
    public void text14() {
        List<Customer> list = customerDao.findByCustNameLikeOrCustAddressIsNullOrCustLevelIsNull("%三%");
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    @Test
    public void text15() {
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path path1 = root.get("custName");
                Predicate predicate1 = criteriaBuilder.equal(path1, "新垣结衣");

                Path<Object> path2 = root.get("custName");
                Predicate predicate2 = criteriaBuilder.equal(path2, "刘亦菲");
                Predicate predicate = criteriaBuilder.or(predicate1, predicate2);
                return predicate;
            }
        };

        List<Customer> list = customerDao.findAll(spec);
        for (Customer customer : list) {
            System.out.println(customer);
        }
      //  Customer customer = customerDao.findOne(spec).orElse(null);
      //  System.out.println(customer);
    }

    @Test
    public void text16() {

        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Path<Object> path = root.get("custName");

                Predicate predicate = cb.like(path.as(String.class), "%亦%");

                return predicate;
            }
        };
        Pageable pageable = PageRequest.of(1, 5);
        Sort sort = new Sort(Sort.Direction.DESC, "custId");

        List<Customer> list = customerDao.findAll(spec, sort);
    }
}
