package uasz.sn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.model.Enseignement;
import uasz.sn.model.EnseignementClient;
import uasz.sn.repository.EnseignementRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EnseignementService {
    @Autowired
    private EnseignementClient enseignementClient;
    @Autowired
    private EnseignementRepository enseignementRepository;

    public Enseignement findById(Long id){
        return enseignementClient.getEnseignement(id);
    }

    public List<Enseignement> findAll() {
        // Récupérer tous les enseignements existants dans la base de données
        List<Enseignement> existingEnseignements = enseignementRepository.findAll();

        // Utiliser un Set pour vérifier plus efficacement l'existence
        Set<Long> existingIds = existingEnseignements.stream()
                .map(Enseignement::getId)
                .collect(Collectors.toSet());

        // Récupérer tous les enseignements depuis le client externe
        List<Enseignement> enseignementsFromClient = enseignementClient.getAllEnseignements();

        // Ajouter seulement ceux qui n'existent pas encore dans la base
        for (Enseignement enseignement : enseignementsFromClient) {
            if (!existingIds.contains(enseignement.getId())) {
                enseignementRepository.save(enseignement);
            }
        }

        // Retourner la liste mise à jour des enseignements
        return enseignementRepository.findAll();
    }


    public void save(Enseignement enseignement){

        List<String> info = enseignementClient.getInfo(enseignement.getId());
        enseignement.setNom(info.get(0));enseignement.setSemetre(info.get(1));enseignement.setNiveau(info.get(2));enseignement.setFormation(info.get(3));
        enseignementRepository.save(enseignement);

    }
    public String findNomById(Long id){
        return enseignementClient.getNom(id);
    }
}
