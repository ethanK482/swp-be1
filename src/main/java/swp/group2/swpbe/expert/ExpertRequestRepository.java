
package swp.group2.swpbe.expert;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import swp.group2.swpbe.expert.entities.ExpertRequest;

@Repository
public interface ExpertRequestRepository extends JpaRepository<ExpertRequest, String> {

}
