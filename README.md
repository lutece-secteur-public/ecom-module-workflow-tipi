![](http://dev.lutece.paris.fr/jenkins/buildStatus/icon?job=ecom-module-workflow-tipi-deploy)
# Module workflow TIPI

## Introduction

Ce module permet d'interagir avec le service TIPI dans un workflow Lutece.

Pour cela, il contient :
 
* une classe abstraite permettant de faire le lien entre les objets m&eacute;tier de votre application et le service TIPI. Voir la section **Cr&eacute;ation d'une t&acirc;che de workflow fournissant les donn&eacute;es TIPI&agrave; partir d'un objet m&eacute;tier** pour plus de d&eacute;tails.
* une t&acirc;che de workflow permettant de configurer les&eacute;tats du worklow lorsque le paiement TIPI est effectu&eacute;. Voir la section **T&acirc;che de workflow de configuration des&eacute;tats apr&egrave;s paiement** pour plus de d&eacute;tails.
* un signet permettant d'ajouter le lien utilisable par l'usager dans une notification. Voir la section **T&acirc;che de workflow de notification** pour plus de d&eacute;tails.
* un d&eacute;mon permettant de r&eacute;cup&eacute;rer les informations des transactions en attente. Voir la section **D&eacute;mon de mise&agrave; jour des paiements** pour plus de d&eacute;tails.


Voici un cin&eacute;matique type applicable avec ce module :
 
* Un usager fait une demande *via* un t&eacute;l&eacute;service (votre plugin m&eacute;tier)
* Un agent traite la demande en back-office. Il appuie sur le bouton d'action de workflow pour d&eacute;clencher la proc&eacute;dure de paiement :
 
* les informations n&eacute;cessaires au paiement TIPI sont retrouv&eacute;es&agrave; partir de votre objet m&eacute;tier (votre t&acirc;che de workflow faisant le lien entre votre plugin m&eacute;tier et TIPI)
* Une notification (email, crm, *etc.* ) est envoy&eacute;e&agrave; l'usager (t&acirc;che *Notifier un usager (guichet, mail et/ou SMS)* ). Cette notification contient un lien.

* L'usager clique sur le lien pr&eacute;sent dans la notification :
 
* ce module appelle le service TIPI pour obtenir un IdOp.
* l'usager est renvoy&eacute; sur la page TIPI.

* L'usager renseigne sa carte bleue sur le service TIPI et valide.
* Le servce TIPI renvoie une notification vers ce module :
 
* en fonction de l'&eacute;tat du paiement (accept&eacute;, refus&eacute; ou abandonn&eacute;), l'&eacute;tat de workflow de votre ressource m&eacute;tier est mis&agrave; jour. Cet&eacute;tat correspond&agrave; l'&eacute;tat configur&eacute; dans la t&acirc;che de workflow *Tache de configuration de TIPI* .
* si une action automatique est pr&eacute;sente pour cet&eacute;tat, elle est ex&eacute;cut&eacute;e. Voir la section **Ex&eacute;cuter une action apr&egrave;s le paiement** pour plus de d&eacute;tails.



## Cr&eacute;ation d'une t&acirc;che de workflow fournissant les donn&eacute;es TIPI &agrave; partir d'un objet m&eacute;tier

Ce module ne peut pas deviner certaines donn&eacute;es essentielles au paiement TIPI :
 
* la r&eacute;f&eacute;rence de la dette
* le montant de la dette
* l'adresse email de l'utilisateur
Il faut donc cr&eacute;er une t&acirc;che de workflow qui fournit ces donn&eacute;es&agrave; partir de vos objets m&eacute;tier.

Ce module fournit une t&acirc;che de workflow abstraite que vous devez&eacute;tendre pour fournir les donn&eacute;es : `fr.paris.lutece.plugins.workflow.modules.tipi.service.task.AbstractTipiProviderTask` . Vous devez alors impl&eacute;menter les m&eacute;thodes suivantes :
 
*  `String getTitle( Locale )` : fournit le titre de votre t&acirc;che en fonction de la `Locale` pass&eacute;e en param&egrave;tre. Cette m&eacute;thode n'est pas sp&eacute;cifique&agrave; la classe `AbstractTipiProviderTask` mais provient de l'interface `fr.paris.lutece.plugins.workflowcore.service.task.ITask` du workflow.
*  `String provideRefDet( ResourceHistory )` : fournit la r&eacute;f&eacute;rence de la dette&agrave; partir de l'objet `ResourceHistory` pass&eacute; en param&egrave;tre.
*  `int provideAmount( ResourceHistory )` : fournit le montant de la dette&agrave; partir de l'objet `ResourceHistory` pass&eacute; en param&egrave;tre. Ce montant est en **centimes** .
*  `String provideEmail( ResourceHistory )` : fournit l'adresse email de l'usager&agrave; partir de l'objet `ResourceHistory` pass&eacute; en param&egrave;tre.


Voici un exemple d'impl&eacute;mentation :

```

public class MyTipiProviderTask extends AbstractTipiProviderTask
{
    @Inject
    public MyTipiProviderTask( IResourceHistoryService resourceHistoryService, ITipiService tipiService, ITipiRefDetHistoryService tipiRefDetHistoryService )
    {
        super( resourceHistoryService, tipiService, tipiRefDetHistoryService );

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle( Locale local )
    {
        return I18nService.getLocalizedString( MESSAGE_TASK_TITLE, local );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String provideRefDet( ResourceHistory resourceHistory )
    {
        return DatastoreService.getDataValue( DSKEY_REFDET, StringUtils.EMPTY );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int provideAmount( ResourceHistory resourceHistory )
    {
        return NumberUtils.toInt( DatastoreService.getDataValue( DSKEY_AMOUNT, StringUtils.EMPTY ), 0 );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String provideEmail( ResourceHistory resourceHistory )
    {
        return DatastoreService.getDataValue( DSKEY_EMAIL, StringUtils.EMPTY );
    }

}
                
```

Vous pouvez bien s&ucirc;r red&eacute;finir les m&eacute;thodes classiques des t&acirc;ches de workflow, pour ajouter une configuration&agrave; la t&acirc;che par exemple.

Vous devez ensuite d&eacute;clarer votre t&acirc;che dans le contexte Spring, comme toute autre t&acirc;che de workflow :

```

<bean id="workflow-tipimymodule.myTipiProviderTask"
    class="fr.paris.lutece.plugins.workflow.modules.tipimymodule.service.task.myTipiProviderTask"
    scope="prototype" />

<bean id="workflow-tipimymodule.myTipiProviderTypeTask"
    class="fr.paris.lutece.plugins.workflowcore.business.task.TaskType"
    p:key="myTipiProviderTypeTask"
    p:titleI18nKey="module.workflow.tipimymodule.task_my_provider_title"
    p:beanName="workflow-tipimymodule.myTipiProviderTask"
    p:taskForAutomaticAction="true" />
                
```

## Configuration

Le fichier de configuration du module se trouve dans : `WEB-INF/conf/plugins/workflow-tipi.properties` .

Surcharger ce fichier pour y indiquer votre configuration (num&eacute;ro client, objet, *etc.* ).

## Utilisation

 **T&acirc;ches de workflow** 

Pour pouvoir interagir avec le service TIPI dans le workflow Lutece, il faut utiliser 3 t&acirc;ches de workflow dans l'action permettant de demander&agrave; l'usager de payer sa dette sur le service TIPI.

![](http://dev.lutece.paris.fr/plugins/module-workflow-tipi/images/workflow_action.png)

 **Note :** Les 3 t&acirc;ches peuvent&ecirc;tre utilis&eacute;es dans une action automatique. Cela permet de d&eacute;clencher la proc&eacute;dure de paiement de mani&egrave;re automatique.

 **Note :** Si votre workflow contient plusieurs actions permettant de demander&agrave; l'usager de payer sa dette (action de relance par exemple), il faut que les 3 t&acirc;ches soient dans toutes les actions. Les&eacute;tats utilis&eacute;s apr&egrave;s la notification du service TIPI sont ceux de la derni&egrave;re action d&eacute;clench&eacute;e.



 **T&acirc;che de workflow fournissant les donn&eacute;es TIPI&agrave; partir d'un objet m&eacute;tier** 

La premi&egrave;re t&acirc;che n&eacute;cessaire est votre t&acirc;che fournissant les donn&eacute;es TIPI&agrave; partir de votre objet m&eacute;tier. Voir la section *Cr&eacute;ation d'une t&acirc;che de workflow fournissant les donn&eacute;es TIPI&agrave; partir d'un objet m&eacute;tier* pour impl&eacute;menter cette t&acirc;che.



 **T&acirc;che de workflow de configuration des&eacute;tats apr&egrave;s paiement** 

La deuxi&egrave;me t&acirc;che n&eacute;cessaire est la t&acirc;che nomm&eacute;e *Tache de configuration de TIPI* . Elle doit&ecirc;tre configur&eacute;e pour d&eacute;terminer sur quel&eacute;tat de workflow sera positionn&eacute; votre ressource m&eacute;tier lorsque :
 
* le paiement a&eacute;t&eacute; r&eacute;alis&eacute; avec succ&egrave;s par le service TIPI
* le paiement a&eacute;t&eacute; refus&eacute; par le service TIPI.
* le paiement a&eacute;t&eacute; abandonn&eacute;e par l'usager.


![](http://dev.lutece.paris.fr/plugins/module-workflow-tipi/images/task_tipi_configuration.png)



 **T&acirc;che de workflow de notification** 

La troisi&egrave;me t&acirc;che n&eacute;cessaire est la t&acirc;che nomm&eacute;e *Notifier un usager (guichet, mail et/ou SMS)* . Ce module expose un signet `${tipi_url!}` permettant d'ins&eacute;rer l'URL de traitement de paiements. Vous pouvez ajouter ce signet dans l'attribut `href` de la balise `a` pour ajouter un lien cliquable dans la notification :

```
<a href="${tipi_url!}">Lien de paiement</a>
```

Pour que ce signet soit disponible, il faut cocher les signets suppl&eacute;mentaires `TIPI` dans la configuration avanc&eacute;e.

Voir le **module-workflow-notifygru** pour plus de d&eacute;tails.



 **D&eacute;mon de mise&agrave; jour des paiements** 

Ce d&eacute;mon r&eacute;cup&egrave;re tous les paiements qui ont&eacute;t&eacute; initialis&eacute;s par l'usager (clic sur le lien pr&eacute;sent dans la notification) mais dont le r&eacute;sultat n'a pas&eacute;t&eacute; fourni par le service TIPI. Ce cas peut arriver s'il y a eu un probl&egrave;me r&eacute;seau par exemple. Pour tous ces paiements, le d&eacute;mon interroge le service TIPI afin de les mettre&agrave; jour dans Lutece.

Par d&eacute;faut, ce d&eacute;mon n'est pas activ&eacute;. Si vous d&eacute;cidez de l'activer, Il est conseill&eacute; de soit le d&eacute;sactiver apr&egrave;s la mise&agrave; jour des paiements, soit le laisser activ&eacute; mais avec un intervalle de 24h minimum, afin de laisser le temps au service TIPI de notifier le module pour les transactions en cours. Mettre un intervalle trop court risquerait d'interf&eacute;rer avec le m&eacute;canisme nominal du service TIPI.



 **Ex&eacute;cuter une action apr&egrave;s le paiement** 

Apr&egrave;s la notification par le service TIPI du r&eacute;sultat de la transaction, il est possible d'ex&eacute;cuter automatiquement une action de workflow.

 **Note :** L'explication ci-dessous se base sur une transaction accept&eacute;e, mais le m&ecirc;me principe peut&ecirc;tre appliqu&eacute; sur une transaction refus&eacute;e ou abandonn&eacute;e.

Lorsque le service TIPI notifie ce module d'une transaction accept&eacute;e, le traitement appelle la premi&egrave;re action automatique du workflow dont l'&eacute;tat initial est l'&eacute;tat d&eacute;fini dans la configuration de la t&acirc;che *Tache de configuration de TIPI* pour un paiement accept&eacute;.

Par cons&eacute;quent, pour ex&eacute;cuter automatiquement une action du workflow apr&egrave;s la notification du service TIPI :
 
* Cr&eacute;ez un&eacute;tat dans le workflow. Cet&eacute;tat peut&ecirc;tre utilis&eacute; uniquement pour ce but.
* Dans la configuration de la t&acirc;che TIPI, utilisez l'&eacute;tat cr&eacute;&eacute; dans le champ `Etat si paiement accept&eacute;` .
* Cr&eacute;ez une action automatique dont l'&eacute;tat initial est l'&eacute;tat cr&eacute;&eacute; et l'&eacute;tat final est l'&eacute;tat de votre choix.
* Ajoutez les t&acirc;ches de votre choix dans cette action.


Voici un exemple :

Etats du workflow. L'&eacute;tat `Paiement accept&eacute;` n'est utilis&eacute; que pour ex&eacute;cuter l'action automatique apr&egrave;s notification par le service TIPI.

![](http://dev.lutece.paris.fr/plugins/module-workflow-tipi/images/workflow_with_action_after_paiement_states.png)

Actions du workflow.

![](http://dev.lutece.paris.fr/plugins/module-workflow-tipi/images/workflow_with_action_after_paiement_actions.png)

L'action `Payer` contient les 3 t&acirc;ches de workflow n&eacute;cessaires au paiement de la dette. La t&acirc;che *Tache de configuration de TIPI* est configur&eacute;e pour pointer sur l'&eacute;tat `Paiement accept&eacute;` lorsque le paiement est accept&eacute;.

L'action `Notifier paiement accept&eacute;` est une action **automatique** . C'est cette action qui sera ex&eacute;cut&eacute;e automatiquement apr&egrave;s notification d'un paiement accept&eacute; par le service TIPI. Elle contient les t&acirc;ches souhait&eacute;es. Par exemple, on peut notifier l'usager que son paiement a&eacute;t&eacute; accept&eacute; :

![](http://dev.lutece.paris.fr/plugins/module-workflow-tipi/images/workflow_with_action_after_paiement_action_configuration.png)



 **Lien dans la notification** 

Lorsque l'usager clique sur le lien pr&eacute;sent dans la notification, il est renvoy&eacute; vers le service TIPI uniquement si le paiement ne s'est pas d&eacute;j&agrave; effectu&eacute; avec succ&egrave;s. Dans le cas o&ugrave; le paiement s'est d&eacute;j&agrave; effectu&eacute; avec succ&egrave;s, un message d'information s'affiche pour avertir l'usager qu'il ne peut pas payer une deuxi&egrave;me fois.


[Maven documentation and reports](http://dev.lutece.paris.fr/plugins/module-workflow-tipi/)



 *generated by [xdoc2md](https://github.com/lutece-platform/tools-maven-xdoc2md-plugin) - do not edit directly.*