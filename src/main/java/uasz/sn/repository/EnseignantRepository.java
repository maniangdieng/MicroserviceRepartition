package uasz.sn.repository;

import uasz.sn.model.Enseignant;

@RepositoryRestResource
public interface EnseignantRepository extends JpaRepository<Enseignant,Long> {
}
