.class public Lnju/software/rules/entry/CheckActivityRule;
.super Ljava/lang/Object;
.source "CheckActivityRule.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 16
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static isPermissionRedelegation(IILandroid/content/Intent;Landroid/app/Activity;)Z
    .locals 12
    .param p0, "requestCode"    # I
    .param p1, "resultCode"    # I
    .param p2, "data"    # Landroid/content/Intent;
    .param p3, "act"    # Landroid/app/Activity;

    .prologue
    const/4 v8, 0x1

    const/4 v9, 0x0

    .line 29
    const/4 v1, 0x0

    .line 30
    .local v1, "callerPackage":Ljava/lang/String;
    if-nez p1, :cond_0

    if-nez p0, :cond_0

    if-eqz p2, :cond_0

    .line 31
    const-string v10, "package"

    invoke-virtual {p2, v10}, Landroid/content/Intent;->hasExtra(Ljava/lang/String;)Z

    move-result v10

    if-eqz v10, :cond_0

    .line 32
    invoke-virtual {p2}, Landroid/content/Intent;->getExtras()Landroid/os/Bundle;

    move-result-object v10

    const-string v11, "package"

    invoke-virtual {v10, v11}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v10

    if-eqz v10, :cond_0

    .line 33
    invoke-virtual {p2}, Landroid/content/Intent;->getExtras()Landroid/os/Bundle;

    move-result-object v10

    const-string v11, "package"

    invoke-virtual {v10, v11}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 37
    :cond_0
    new-instance v3, Ljava/util/HashSet;

    invoke-direct {v3}, Ljava/util/HashSet;-><init>()V

    .line 38
    .local v3, "callerPermissionsSet":Ljava/util/Set;, "Ljava/util/Set<Ljava/lang/String;>;"
    new-instance v0, Ljava/util/HashSet;

    invoke-direct {v0}, Ljava/util/HashSet;-><init>()V

    .line 39
    .local v0, "calleePermissionSet":Ljava/util/Set;, "Ljava/util/Set<Ljava/lang/String;>;"
    invoke-virtual {p3}, Landroid/app/Activity;->getPackageName()Ljava/lang/String;

    move-result-object v6

    .line 40
    .local v6, "selfPackage":Ljava/lang/String;
    if-eqz v1, :cond_3

    invoke-virtual {v1, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v10

    if-nez v10, :cond_3

    .line 41
    invoke-static {v6, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 43
    const/4 v5, 0x0

    .line 44
    .local v5, "packageInfo":Landroid/content/pm/PackageInfo;
    :try_start_0
    invoke-virtual {p3}, Landroid/app/Activity;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v10

    invoke-virtual {p3}, Landroid/app/Activity;->getPackageManager()Landroid/content/pm/PackageManager;

    const/16 v11, 0x1000

    invoke-virtual {v10, v1, v11}, Landroid/content/pm/PackageManager;->getPackageInfo(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;

    move-result-object v5

    .line 45
    iget-object v2, v5, Landroid/content/pm/PackageInfo;->requestedPermissions:[Ljava/lang/String;

    .line 46
    .local v2, "callerPermissions":[Ljava/lang/String;
    if-eqz v2, :cond_1

    .line 47
    invoke-static {v3, v2}, Ljava/util/Collections;->addAll(Ljava/util/Collection;[Ljava/lang/Object;)Z

    .line 49
    :cond_1
    const/4 v10, 0x1

    new-array v7, v10, [Ljava/lang/String;

    const/4 v10, 0x0

    const-string v11, "android.permission.SEND_SMS"

    aput-object v11, v7, v10

    .line 50
    .local v7, "selfPermissions":[Ljava/lang/String;
    if-eqz v7, :cond_2

    .line 51
    invoke-static {v0, v7}, Ljava/util/Collections;->addAll(Ljava/util/Collection;[Ljava/lang/Object;)Z

    .line 53
    :cond_2
    invoke-interface {v0, v3}, Ljava/util/Set;->removeAll(Ljava/util/Collection;)Z

    .line 54
    invoke-interface {v0}, Ljava/util/Set;->size()I
    :try_end_0
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    move-result v10

    if-lez v10, :cond_3

    .line 62
    .end local v2    # "callerPermissions":[Ljava/lang/String;
    .end local v5    # "packageInfo":Landroid/content/pm/PackageInfo;
    .end local v7    # "selfPermissions":[Ljava/lang/String;
    :goto_0
    return v8

    .line 58
    .restart local v5    # "packageInfo":Landroid/content/pm/PackageInfo;
    :catch_0
    move-exception v4

    .line 59
    .local v4, "e":Landroid/content/pm/PackageManager$NameNotFoundException;
    invoke-virtual {v4}, Landroid/content/pm/PackageManager$NameNotFoundException;->printStackTrace()V

    .end local v4    # "e":Landroid/content/pm/PackageManager$NameNotFoundException;
    .end local v5    # "packageInfo":Landroid/content/pm/PackageInfo;
    :cond_3
    move v8, v9

    .line 62
    goto :goto_0
.end method


# virtual methods
.method public checkStartActivityForResult(Ljava/util/Set;Ljava/util/Set;Landroid/app/Activity;)V
    .locals 0
    .param p3, "act"    # Landroid/app/Activity;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/Set",
            "<",
            "Ljava/lang/String;",
            ">;",
            "Ljava/util/Set",
            "<",
            "Ljava/lang/String;",
            ">;",
            "Landroid/app/Activity;",
            ")V"
        }
    .end annotation

    .prologue
    .line 66
    .local p1, "callerPermissions":Ljava/util/Set;, "Ljava/util/Set<Ljava/lang/String;>;"
    .local p2, "calleePermissions":Ljava/util/Set;, "Ljava/util/Set<Ljava/lang/String;>;"
    return-void
.end method
