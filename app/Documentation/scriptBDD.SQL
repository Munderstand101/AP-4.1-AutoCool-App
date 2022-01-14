drop database if exists autocoolAP;

create database if not exists autocoolAP;
use autocoolAP;
# -----------------------------------------------------------------------------
#       table : user
# -----------------------------------------------------------------------------

create table if not exists user
 (
   nom char(255) not null  ,
   email char(32) null  ,
   mdp char(255) null  
   , primary key (nom) 
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# -----------------------------------------------------------------------------
#       table : lieu
# -----------------------------------------------------------------------------

create table if not exists lieu
 (
   codelieu bigint(4) not null  ,
   codeville bigint(4) not null  ,
   libellelieu char(32) null  
   , primary key (codelieu) 
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# -----------------------------------------------------------------------------
#       table : categorie_vehciule
# -----------------------------------------------------------------------------

create table if not exists categorie_vehciule
 (
   codecateg bigint(4) not null  ,
   libellecateg char(32) null  
   , primary key (codecateg) 
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# -----------------------------------------------------------------------------
#       table : abonne
# -----------------------------------------------------------------------------

create table if not exists abonne
 (
   numabonne bigint(4) not null  ,
   nom char(32) null  ,
   prenom char(32) null  ,
   datenaissance char(32) null  ,
   rue char(32) null  ,
   ville char(32) null  ,
   codepostal char(32) null  ,
   tel char(32) null  ,
   telmobile char(32) null  ,
   email char(32) null  ,
   numpermis char(32) null  ,
   lieupermis char(32) null  ,
   datepermis char(32) null  ,
   paiementadhesion char(32) null  ,
   paiementcaution char(32) null  ,
   ribfourni char(32) null  
   , primary key (numabonne) 
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# -----------------------------------------------------------------------------
#       table : type_vehicule
# -----------------------------------------------------------------------------

create table if not exists type_vehicule
 (
   codetype bigint(4) not null  ,
   codecateg bigint(4) not null  ,
   libelletype char(32) null  
   , primary key (codetype) 
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# -----------------------------------------------------------------------------
#       table : ville
# -----------------------------------------------------------------------------

create table if not exists ville
 (
   codeville bigint(4) not null  ,
   libelleville varchar(128) null  
   , primary key (codeville) 
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# -----------------------------------------------------------------------------
#       table : vehicule
# -----------------------------------------------------------------------------

create table if not exists vehicule
 (
   codevoiture bigint(4) not null  ,
   codetype bigint(4) not null  ,
   codelieu bigint(4) not null  ,
   libellevoiture varchar(128) null  ,
   kilometrage bigint(9) null  ,
   niveauessence real(5,2) null  ,
   nbplace bigint(4) null  ,
   automatiqueo_n bool null  
   , primary key (codevoiture) 
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# -----------------------------------------------------------------------------
#       table : tranche_km
# -----------------------------------------------------------------------------

create table if not exists tranche_km
 (
   codetranchekm bigint(4) not null  ,
   minkm bigint(4) null  ,
   maxkm bigint(4) null  
   , primary key (codetranchekm) 
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# -----------------------------------------------------------------------------
#       table : tranche_horaire
# -----------------------------------------------------------------------------

create table if not exists tranche_horaire
 (
   codetrancheh bigint(4) not null  ,
   duree date null  
   , primary key (codetrancheh) 
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# -----------------------------------------------------------------------------
#       table : formule
# -----------------------------------------------------------------------------

create table if not exists formule
 (
   codeform bigint(4) not null  ,
   libelleform char(32) null  ,
   fraisadhesion decimal(10,2) null  ,
   tarifmensuel decimal(10,2) null  ,
   partsociale decimal(10,2) null  ,
   depotgarantie decimal(10,2) null  ,
   caution decimal(10,2) null  
   , primary key (codeform) 
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# -----------------------------------------------------------------------------
#       table : adhere
# -----------------------------------------------------------------------------

create table if not exists adhere
 (
   numabonne bigint(4) not null  ,
   codeform bigint(4) not null  
   , primary key (numabonne,codeform) 
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# -----------------------------------------------------------------------------
#       table : facturerh
# -----------------------------------------------------------------------------

create table if not exists facturerh
 (
   codeform bigint(4) not null  ,
   codetrancheh bigint(4) not null  ,
   codecateg bigint(4) not null  ,
   tarifh decimal(10,2) null  
   , primary key (codeform,codetrancheh,codecateg) 
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# -----------------------------------------------------------------------------
#       table : facturerkm
# -----------------------------------------------------------------------------

create table if not exists facturerkm
 (
   codetranchekm bigint(4) not null  ,
   codecateg bigint(4) not null  ,
   codeform bigint(4) not null  ,
   tarifkm decimal(10,2) null  
   , primary key (codetranchekm,codecateg,codeform) 
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;


# -----------------------------------------------------------------------------
#       creation des references de table
# -----------------------------------------------------------------------------


alter table lieu 
  add foreign key fk_lieu_ville (codeville)
      references ville (codeville) ;


alter table type_vehicule 
  add foreign key fk_type_vehicule_categorie_vehciule (codecateg)
      references categorie_vehciule (codecateg) ;


alter table vehicule 
  add foreign key fk_vehicule_type_vehicule (codetype)
      references type_vehicule (codetype) ;


alter table vehicule 
  add foreign key fk_vehicule_lieu (codelieu)
      references lieu (codelieu) ;


alter table adhere 
  add foreign key fk_adhere_abonne (numabonne)
      references abonne (numabonne) ;


alter table adhere 
  add foreign key fk_adhere_formule (codeform)
      references formule (codeform) ;


alter table facturerh 
  add foreign key fk_facturerh_formule (codeform)
      references formule (codeform) ;


alter table facturerh 
  add foreign key fk_facturerh_tranche_horaire (codetrancheh)
      references tranche_horaire (codetrancheh) ;


alter table facturerh 
  add foreign key fk_facturerh_categorie_vehciule (codecateg)
      references categorie_vehciule (codecateg) ;


alter table facturerkm 
  add foreign key fk_facturerkm_tranche_km (codetranchekm)
      references tranche_km (codetranchekm) ;


alter table facturerkm 
  add foreign key fk_facturerkm_categorie_vehciule (codecateg)
      references categorie_vehciule (codecateg) ;


alter table facturerkm 
  add foreign key fk_facturerkm_formule (codeform)
      references formule (codeform) ;

