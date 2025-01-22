package com.ayaan.FinanceTracker.models;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "budget_tracker")
public class BudgetTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_tracker_id")
    private Integer budgetTrackerId ;

    @Column(name = "name")
    private String name;

    @Column(name = "budget_percentage")
    private Double budgetPercentage;

    @Transient
    private Double remaining;

    @Transient
    private Double progress;


    public BudgetTracker(){
        
    }

    
    public BudgetTracker(String name, Double remaining, Double budgetPercentage, Double progress){
        this.name = name;
        this.budgetPercentage = budgetPercentage;
        this.remaining = remaining;
        this.progress = progress;
    }


    public Integer getBudgetTrackerId() {
        return budgetTrackerId;
    }
    
    public void setBudgetTrackerId(Integer budgetTrackerId) {
        this.budgetTrackerId = budgetTrackerId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getBudgetPercentage() {
        return budgetPercentage;
    }
    public void setBudgetPercentage(Double budgetPercentage) {
        this.budgetPercentage = budgetPercentage;
    }
    public Double getRemaining() {
        return remaining;
    }
    public void setRemaining(Double remaining) {
        this.remaining = remaining;
    }
    public Double getProgress() {
        return progress;
    }
    public void setProgress(Double progress) {
        this.progress = progress;
    }
}
